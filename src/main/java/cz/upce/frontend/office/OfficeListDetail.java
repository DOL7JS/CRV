package cz.upce.frontend.office;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import cz.upce.frontend.FieldValidator;
import cz.upce.frontend.Menu;
import cz.upce.frontend.errorHandler.ErrorView;
import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.security.SecurityService;
import cz.upce.nnpro_backend.services.BranchOfficeService;
import cz.upce.nnpro_backend.services.ConversionService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import javax.annotation.security.RolesAllowed;
import java.util.NoSuchElementException;
import java.util.Optional;

@Route(value = "offices/:officeID?/:action?(edit)", layout = Menu.class)
@RolesAllowed({"ROLE_Admin", "ROLE_Kraj"})
public class OfficeListDetail extends Composite<VerticalLayout> implements BeforeEnterObserver {

    private final String OFFICE_ID = "officeID";
    private final String OFFICE_EDIT = "offices/%s/edit";

    private final Grid<BranchOfficeDto> grid = new Grid<>(BranchOfficeDto.class, false);

    private TextField region;
    private TextField district;
    private TextField city;


    private final Button cancel = new Button("Zrušit");
    private final Button save = new Button("Uložit");

    private BeanValidationBinder<BranchOfficeDto> binder;

    private BranchOfficeDto branchOfficeDto;


    private final BranchOfficeService branchOfficeService;
    private final SecurityService securityService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> samplePersonId = event.getRouteParameters().get(OFFICE_ID).map(Long::parseLong);
        if (samplePersonId.isPresent()) {
            Optional<BranchOfficeDto> office;
            try {
                office = Optional.ofNullable(branchOfficeService.getOffice(samplePersonId.get()));
            } catch (NoSuchElementException ex) {
                event.forwardTo(ErrorView.class);
                return;
            }
            if (office.isPresent()) {
                if (securityService.isKrajOfficer() &&
                        !securityService.getAuthenticatedUser().getBranchOffice().getRegion().equals(office.get().getRegion())) {
                    event.forwardTo(ErrorView.class);
                    return;
                }

                populateForm(office.get());
            } else {
                Notification.show(
                        String.format("The requested office was not found, ID = %s", samplePersonId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(OfficeListDetail.class);
            }
        }
    }

    public OfficeListDetail(BranchOfficeService branchOfficeService, SecurityService securityService) {
        this.branchOfficeService = branchOfficeService;
        this.securityService = securityService;

        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();
        horizontalLayoutMain.setClassName("horizontalMain");
        createGridLayout(horizontalLayoutMain);
        createEditorLayout(horizontalLayoutMain);
        getContent().add(horizontalLayoutMain);

        configureGrid();
        configureBinder();

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        BeanValidationBinder<BranchOfficeDto> finalBinder = binder;
        save.addClickListener(e -> {
            try {
                boolean isValid = !FieldValidator.validateEmptyField(city)
                        & !FieldValidator.validateEmptyField(district)
                        & !FieldValidator.validateEmptyField(region);

                if (!isValid) {
                    Notification.show("Vyplňte všechna pole.", 3000, Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                if (this.branchOfficeDto == null) {
                    this.branchOfficeDto = new BranchOfficeDto();
                    setBranchOfficeDto();
                    BranchOffice branchOffice = branchOfficeService.addOffice(ConversionService.convertToBranchOfficeInDto(branchOfficeDto));
                    this.branchOfficeDto = ConversionService.convertToOfficeDto(branchOffice);
                    finalBinder.writeBean(this.branchOfficeDto);
                    clearForm();
                    refreshGrid();
                    Notification.show("Stanice přidána.", 3000, Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } else {
                    setBranchOfficeDto();
                    BranchOffice branchOffice = branchOfficeService.editOffice(this.branchOfficeDto.getId(), ConversionService.convertToBranchOfficeInDto(this.branchOfficeDto));
                    grid.getDataProvider().refreshItem(this.branchOfficeDto);
                    this.branchOfficeDto = ConversionService.convertToOfficeDto(branchOffice);
                    Notification.show("Stanice aktualizována.", 3000, Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }

            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalArgumentException ex) {
                Notification n = Notification.show(
                        ex.getMessage());
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    private void setBranchOfficeDto() {
        branchOfficeDto.setCity(city.getValue());
        branchOfficeDto.setRegion(region.getValue());
        branchOfficeDto.setDistrict(district.getValue());
    }

    private void configureGrid() {
        grid.addColumn(BranchOfficeDto::getRegion).setAutoWidth(true).setHeader("Kraj");
        grid.addColumn(BranchOfficeDto::getDistrict).setAutoWidth(true).setHeader("Okres");
        grid.addColumn(BranchOfficeDto::getCity).setAutoWidth(true).setHeader("Město");

        if (securityService.isAdmin()) {
            grid.setItems(branchOfficeService.getAllOffices());
        } else if (securityService.isKrajOfficer()) {
            grid.setItems(branchOfficeService.getOfficesByRegion(securityService.getAuthenticatedUser().getBranchOffice()));
        }
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(OFFICE_EDIT, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(OfficeListDetail.class);
            }
        });
    }

    private void configureBinder() {
        binder = new BeanValidationBinder<>(BranchOfficeDto.class);
        binder.bindInstanceFields(this);
        binder.forField(city).withValidator(name -> name.length() > 0, "City must have at least 1 character").bind(BranchOfficeDto::getCity, BranchOfficeDto::setCity);
        binder.forField(district).withValidator(name -> name.length() > 0, "District must have at least 1 character").bind(BranchOfficeDto::getDistrict, BranchOfficeDto::setDistrict);
        binder.forField(region).withValidator(name -> name.length() > 0, "Region must have at least 1 character").bind(BranchOfficeDto::getRegion, BranchOfficeDto::setRegion);
    }

    private void createEditorLayout(HorizontalLayout splitLayout) {
        VerticalLayout verticalLayoutForFields = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassNames(LumoUtility.Gap.MEDIUM);
        verticalLayoutForFields.getStyle().set("width", "500 px");
        verticalLayoutForFields.setWidth("500 px");
        region = new TextField("Kraj");
        district = new TextField("Okres");
        city = new TextField("Město");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        verticalLayoutForFields.add(region, district, city);
        verticalLayoutForFields.add(buttonLayout);
        buttonLayout.add(save, cancel);
        splitLayout.add(verticalLayoutForFields);
    }

    private void createGridLayout(HorizontalLayout horizontalLayout) {
        getContent().setHeightFull();
        getContent().setWidthFull();
        horizontalLayout.setWidthFull();
        getContent().setFlexGrow(1.0, horizontalLayout);
        horizontalLayout.addClassName(LumoUtility.Gap.MEDIUM);
        horizontalLayout.setFlexGrow(1.0, grid);
        grid.setHeightFull();
        horizontalLayout.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
        if (securityService.isAdmin()) {
            grid.setItems(branchOfficeService.getAllOffices());
        } else if (securityService.isKrajOfficer()) {
            grid.setItems(branchOfficeService.getOfficesByRegion(securityService.getAuthenticatedUser().getBranchOffice()));
        }
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(BranchOfficeDto value) {
        this.branchOfficeDto = value;
        binder.readBean(this.branchOfficeDto);

    }
}
