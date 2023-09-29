package cz.upce.frontend.office;

import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;

import java.util.Optional;

import cz.upce.frontend.FieldValidator;
import cz.upce.frontend.Menu;
import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.services.BranchOfficeService;
import cz.upce.nnpro_backend.services.ConversionService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@Route(value = "offices/:officeID?/:action?(edit)", layout = Menu.class)
public class OfficeDetail extends Div implements BeforeEnterObserver {

    private final String OFFICE_ID = "officeID";
    private final String OFFICE_EDIT = "offices/%s/edit";

    private final Grid<BranchOfficeDto> grid = new Grid<>(BranchOfficeDto.class, false);

    private TextField region;
    private TextField district;
    private TextField city;


    private final Button cancel = new Button("Zrušit");
    private final Button save = new Button("Uložit");

    private final BeanValidationBinder<BranchOfficeDto> binder;

    private BranchOfficeDto branchOfficeDto;


    private final BranchOfficeService branchOfficeService;

    public OfficeDetail(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
        addClassNames("master-detail-view");

        // Create UI
        HorizontalLayout splitLayout = new HorizontalLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn(BranchOfficeDto::getRegion).setAutoWidth(true).setHeader("Kraj");
        grid.addColumn(BranchOfficeDto::getDistrict).setAutoWidth(true).setHeader("Okres");
        grid.addColumn(BranchOfficeDto::getCity).setAutoWidth(true).setHeader("Město");

        grid.setItems(branchOfficeService.getAllOffices());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(OFFICE_EDIT, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(OfficeDetail.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(BranchOfficeDto.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);
        binder.forField(city).withValidator(name -> name.length() > 0, "City must have at least 1 character").bind(BranchOfficeDto::getCity, BranchOfficeDto::setCity);
        binder.forField(district).withValidator(name -> name.length() > 0, "District must have at least 1 character").bind(BranchOfficeDto::getDistrict, BranchOfficeDto::setDistrict);
        binder.forField(region).withValidator(name -> name.length() > 0, "Region must have at least 1 character").bind(BranchOfficeDto::getRegion, BranchOfficeDto::setRegion);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        BeanValidationBinder<BranchOfficeDto> finalBinder = binder;
        save.addClickListener(e -> {
            try {
                boolean isValid = !FieldValidator.validateEmptyField(city) & !FieldValidator.validateEmptyField(district) &
                        !FieldValidator.validateEmptyField(region);
                if (!isValid) {
                    Notification.show("Vyplňte všechna pole.", 3000, Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                if (this.branchOfficeDto == null) {
                    this.branchOfficeDto = new BranchOfficeDto();
                    branchOfficeDto.setCity(city.getValue());
                    branchOfficeDto.setRegion(region.getValue());
                    branchOfficeDto.setDistrict(district.getValue());
                    branchOfficeService.addOffice(ConversionService.convertToBranchOfficeInDto(branchOfficeDto));
                    Notification.show("Stanice přidána.");
                } else {
                    branchOfficeDto.setCity(city.getValue());
                    branchOfficeDto.setRegion(region.getValue());
                    branchOfficeDto.setDistrict(district.getValue());
                    branchOfficeService.editOffice(branchOfficeDto.getId(), ConversionService.convertToBranchOfficeInDto(branchOfficeDto));
                    Notification.show("Stanice aktualizována.");
                }
                finalBinder.writeBean(this.branchOfficeDto);
                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(OfficeDetail.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> samplePersonId = event.getRouteParameters().get(OFFICE_ID).map(Long::parseLong);
        if (samplePersonId.isPresent()) {
            Optional<BranchOfficeDto> office = Optional.ofNullable(branchOfficeService.getOffice(samplePersonId.get()));
            if (office.isPresent()) {
                populateForm(office.get());
            } else {
                Notification.show(
                        String.format("The requested office was not found, ID = %s", samplePersonId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(OfficeDetail.class);
            }
        }
    }

    private void createEditorLayout(HorizontalLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        region = new TextField("Kraj");
        district = new TextField("Okres");
        city = new TextField("Město");
        formLayout.add(region, district, city);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);
        splitLayout.add(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(HorizontalLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.add(wrapper);
        splitLayout.setFlexGrow(1.0, wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
        grid.setItems(branchOfficeService.getAllOffices());

    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(BranchOfficeDto value) {
        this.branchOfficeDto = value;
        binder.readBean(this.branchOfficeDto);

    }
}
