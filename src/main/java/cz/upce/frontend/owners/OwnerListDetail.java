package cz.upce.frontend.owners;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.lumo.LumoUtility;
import cz.upce.frontend.DoubleToIntegerConverter;
import cz.upce.frontend.FieldValidator;
import cz.upce.frontend.Menu;
import cz.upce.frontend.cars.CarDetail;
import cz.upce.nnpro_backend.dtos.CarDto;
import cz.upce.nnpro_backend.dtos.OwnerInDto;
import cz.upce.nnpro_backend.dtos.OwnerOutDto;
import cz.upce.nnpro_backend.security.SecurityService;
import cz.upce.nnpro_backend.services.OwnerService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@Route(value = "owners/:ownerID?/:action?(edit)", layout = Menu.class)
@PermitAll
public class OwnerListDetail extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final String OWNER_ID = "ownerID";
    private final String OWNER_EDIT = "owners/%s/edit";

    private final Grid<OwnerOutDto> grid = new Grid<>(OwnerOutDto.class, false);

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private DatePicker birthDate;
    private TextField city;
    private TextField street;
    private NumberField numberOfHouse;
    private NumberField zipCode;


    private final Button cancel = new Button("Zrušit");
    private final Button save = new Button("Uložit");

    private final BeanValidationBinder<OwnerOutDto> binder;

    private OwnerOutDto ownerOutDto;

    private final OwnerService ownerService;
    private final SecurityService securityService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> ownerId = event.getRouteParameters().get(OWNER_ID).map(Long::parseLong);
        if (ownerId.isPresent()) {
            Optional<OwnerOutDto> owner = Optional.ofNullable(ownerService.getOwner(ownerId.get()));
            if (owner.isPresent()) {
                if (securityService.isKrajOfficer()) {
                    openOwnerDialog(owner.get());
                } else {
                    populateForm(owner.get());
                }
                grid.select(owner.get());
            } else {
                Notification.show(
                        String.format("The requested owner was not found, ID = %s", ownerId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(OwnerListDetail.class);
            }
        }
    }

    public OwnerListDetail(OwnerService ownerService, SecurityService securityService) {
        this.ownerService = ownerService;
        this.securityService = securityService;
        getContent().addClassNames("master-detail-view");

        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();

        createGridLayout(horizontalLayoutMain);
        binder = new BeanValidationBinder<>(OwnerOutDto.class);
        if (!this.securityService.isKrajOfficer()) {
            createEditorLayout(horizontalLayoutMain);
            configureBinder();
            BeanValidationBinder<OwnerOutDto> finalBinder = binder;
            save.addClickListener(e -> {
                try {
                    boolean isValid = !FieldValidator.validateEmptyField(firstName)
                            & !FieldValidator.validateEmptyField(lastName)
                            & !FieldValidator.validateEmptyField(email)
                            & !FieldValidator.validateEmptyField(birthDate)
                            & !FieldValidator.validateEmptyField(city)
                            & !FieldValidator.validateEmptyField(street)
                            & !FieldValidator.validateEmptyField(numberOfHouse)
                            & !FieldValidator.validateEmptyField(zipCode);
                    if (!isValid) {
                        Notification.show("Vyplňte všechna pole.", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                        return;
                    }
                    if (this.ownerOutDto == null) {
                        ownerService.addOwner(setOwnerInDto());
                        Notification.show("Stanice přidána.");
                    } else {
                        ownerService.editOwner(ownerOutDto.getId(), setOwnerInDto());
                        Notification.show("Stanice aktualizována.");
                    }
                    setOwnerOutDto();
                    finalBinder.writeBean(this.ownerOutDto);
                    clearForm();
                    refreshGrid();
                    UI.getCurrent().navigate(OwnerListDetail.class);
                } catch (ObjectOptimisticLockingFailureException exception) {
                    Notification n = Notification.show(
                            "Error updating the data. Somebody else has updated the record while you were making changes.");
                    n.setPosition(Notification.Position.MIDDLE);
                    n.addThemeVariants(NotificationVariant.LUMO_ERROR);
                } catch (ValidationException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        getContent().add(horizontalLayoutMain);
        configureGrid();


    }

    private void openOwnerDialog(OwnerOutDto item) {
        Dialog dialog = new Dialog();
        VerticalLayout verticalLayoutMain = new VerticalLayout();
        H1 h1FirstName = new H1();
        H3 h3FirstName = new H3();
        Paragraph paragraphFirstName = new Paragraph();
        H3 h3LastName = new H3();
        Paragraph paragraphLastName = new Paragraph();
        H3 h3Email = new H3();
        Paragraph paragraphEmail = new Paragraph();
        H3 h3BirthDate = new H3();
        Paragraph paragraphBirthDate = new Paragraph();
        H3 h3City = new H3();
        Paragraph paragraphCity = new Paragraph();
        H3 h3Street = new H3();
        Paragraph paragraphStreet = new Paragraph();
        H3 h3HouseNumber = new H3();
        Paragraph paragraphHouseNumber = new Paragraph();
        H3 h3ZipCode = new H3();
        Paragraph paragraphZipCode = new Paragraph();

        HorizontalLayout layoutRowFirstName = new HorizontalLayout();
        HorizontalLayout layoutRowButtons = new HorizontalLayout();
        HorizontalLayout layoutRowLastName = new HorizontalLayout();
        HorizontalLayout layoutRowEmail = new HorizontalLayout();
        HorizontalLayout layoutRowBirthDate = new HorizontalLayout();
        HorizontalLayout layoutRowCity = new HorizontalLayout();
        HorizontalLayout layoutRowStreet = new HorizontalLayout();
        HorizontalLayout layoutRowHouseNumber = new HorizontalLayout();
        HorizontalLayout layoutRowZipCode = new HorizontalLayout();
        Button buttonCarHistory = new Button();
        Button buttonClose = new Button();
        verticalLayoutMain.addClassName(LumoUtility.Gap.XSMALL);
        verticalLayoutMain.setHeightFull();
        verticalLayoutMain.setWidthFull();
        h1FirstName.setText("Vlastník");

        h3FirstName.setText("Jméno");
        paragraphFirstName.setText(item.getFirstName());
        h3LastName.setText("Příjmení");
        paragraphLastName.setText(item.getLastName());
        h3Email.setText("Email");
        paragraphEmail.setText(item.getEmail());
        h3BirthDate.setText("Datum narození");
        paragraphBirthDate.setText(item.getBirthDate().toString());
        h3City.setText("Město");
        paragraphCity.setText(item.getCity());
        h3Street.setText("Ulice");
        paragraphStreet.setText(item.getStreet());
        h3HouseNumber.setText("Číslo popisné");
        paragraphHouseNumber.setText(String.valueOf(item.getNumberOfHouse()));
        h3ZipCode.setText("PSČ");
        paragraphZipCode.setText(String.valueOf(item.getZipCode()));

        layoutRowButtons.addClassName(LumoUtility.Gap.MEDIUM);
        buttonCarHistory.setText("Historie vozidel");
        buttonCarHistory.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonClose.setText("Zavřít");
        buttonClose.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        verticalLayoutMain.add(h1FirstName);

        verticalLayoutMain.add(setRowInDialog(layoutRowFirstName, h3FirstName, paragraphFirstName));
        verticalLayoutMain.add(setRowInDialog(layoutRowLastName, h3LastName, paragraphLastName));
        verticalLayoutMain.add(setRowInDialog(layoutRowEmail, h3Email, paragraphEmail));
        verticalLayoutMain.add(setRowInDialog(layoutRowBirthDate, h3BirthDate, paragraphBirthDate));
        verticalLayoutMain.add(setRowInDialog(layoutRowCity, h3City, paragraphCity));
        verticalLayoutMain.add(setRowInDialog(layoutRowStreet, h3Street, paragraphStreet));
        verticalLayoutMain.add(setRowInDialog(layoutRowHouseNumber, h3HouseNumber, paragraphHouseNumber));
        verticalLayoutMain.add(setRowInDialog(layoutRowZipCode, h3ZipCode, paragraphZipCode));

        verticalLayoutMain.add(layoutRowButtons);
        layoutRowButtons.add(buttonCarHistory);
        layoutRowButtons.add(buttonClose);
        buttonCarHistory.addClickListener(event -> {
            dialog.close();
            openAllCarsDialog(item.getId());
            grid.deselectAll();
        });
        buttonClose.addClickListener(event -> {
            grid.deselectAll();
            dialog.close();
        });
        dialog.add(verticalLayoutMain);
        dialog.open();
    }

    private HorizontalLayout setRowInDialog(HorizontalLayout horizontalLayout, H3 h3, Paragraph paragraph) {
        horizontalLayout.setAlignSelf(FlexComponent.Alignment.BASELINE, h3);
        horizontalLayout.setAlignSelf(FlexComponent.Alignment.BASELINE, paragraph);
        horizontalLayout.add(h3);
        horizontalLayout.add(paragraph);
        horizontalLayout.addClassName(LumoUtility.Gap.MEDIUM);
        paragraph.getStyle().set("font-size", "var(--lumo-font-size-m)");
        return horizontalLayout;
    }


    private void createEditorLayout(HorizontalLayout splitLayout) {
        VerticalLayout verticalLayoutForFields = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassNames(LumoUtility.Gap.MEDIUM);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);

        verticalLayoutForFields.getStyle().set("width", "500 px");
        verticalLayoutForFields.setWidth("500 px");

        firstName = new TextField("Jméno");
        lastName = new TextField("Příjmení");
        birthDate = new DatePicker("Datum narození");
        email = new TextField("Email");
        city = new TextField("Město");
        street = new TextField("Ulice");
        numberOfHouse = new NumberField("Čislo popisné");
        zipCode = new NumberField("PSČ");
        verticalLayoutForFields.add(firstName, lastName, email, birthDate, city, street, numberOfHouse, zipCode);

        verticalLayoutForFields.add(buttonLayout);
        Button buttonAllCars = new Button("Historie vlastnictví");
        buttonAllCars.setWidthFull();
        buttonAllCars.addClickListener(click -> {
            openAllCarsDialog(ownerOutDto.getId());
        });
        verticalLayoutForFields.add(buttonAllCars);
        splitLayout.add(verticalLayoutForFields);
    }

    private void openAllCarsDialog(Long ownerID) {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Historie vlastnictví");
        dialog.setWidth(500, Unit.PIXELS);
        VerticalLayout dialogLayout = createDialogLayout(dialog, ownerID);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private VerticalLayout createDialogLayout(Dialog dialog, Long ownerId) {
        H2 headline = new H2("Historie vlastnictví");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        Grid<CarDto> gridCars = new Grid<>();
        gridCars.addColumn(CarDto::getManufacturer).setHeader("Výrobce").setAutoWidth(true);
        gridCars.addColumn(CarDto::getType).setHeader("Model").setAutoWidth(true);
        gridCars.addColumn(CarDto::getStartOfSignUp).setHeader("Přihlášení").setAutoWidth(true);
        Grid.Column<CarDto> signOutColumn = gridCars.addColumn(CarDto::getEndOfSignUp).setHeader("Odhlášení").setAutoWidth(true);
        gridCars.setItems(ownerService.getOwnerCars(ownerId));
        GridSortOrder<CarDto> sort = new GridSortOrder<>(signOutColumn, SortDirection.DESCENDING);
        gridCars.sort(List.of(sort));
        gridCars.addItemClickListener(item -> {
            if (item.getClickCount() == 2) {
                gridCars.getUI().ifPresent(ui -> ui.navigate(CarDetail.class, new RouteParameters("carID", item.getItem().getId().toString())));
                dialog.close();
            }
        });
        Button cancelButton = new Button("Zavřít", e -> {
            dialog.close();
        });
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, gridCars,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        return dialogLayout;
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
        grid.setItems(ownerService.getAllOwners());

    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(OwnerOutDto value) {
        this.ownerOutDto = value;
        binder.readBean(this.ownerOutDto);
    }

    private void configureGrid() {
        grid.addColumn(OwnerOutDto::getFirstName).setAutoWidth(true).setHeader("Jméno").setSortable(true).setComparator(item -> item.getFirstName().toUpperCase());
        grid.addColumn(OwnerOutDto::getLastName).setAutoWidth(true).setHeader("Příjmení").setSortable(true).setComparator(item -> item.getLastName().toUpperCase());
        grid.addColumn(OwnerOutDto::getEmail).setAutoWidth(true).setHeader("Email").setSortable(true).setComparator(item -> item.getEmail().toUpperCase());
        grid.addColumn(OwnerOutDto::getBirthDate).setAutoWidth(true).setHeader("Datum narození").setSortable(true);
        grid.addColumn(OwnerOutDto::getCity).setAutoWidth(true).setHeader("Město").setSortable(true).setComparator(item -> item.getCity().toUpperCase());
        grid.addColumn(OwnerOutDto::getStreet).setAutoWidth(true).setHeader("Ulice").setSortable(true).setComparator(item -> item.getStreet().toUpperCase());
        grid.addColumn(OwnerOutDto::getNumberOfHouse).setAutoWidth(true).setHeader("Číslo popisné").setSortable(true);
        grid.addColumn(OwnerOutDto::getZipCode).setAutoWidth(true).setHeader("PSČ").setSortable(true);

        grid.setItems(ownerService.getAllOwners());
        if (ownerOutDto != null) {
            grid.select(ownerOutDto);
        }
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(OWNER_EDIT, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(OwnerListDetail.class);
            }
        });
    }

    private void configureBinder() {
        binder.forField(firstName).withValidator(name -> name.length() > 0, "Jméno must have at least 1 character").bind(OwnerOutDto::getFirstName, OwnerOutDto::setFirstName);
        binder.forField(lastName).withValidator(name -> name.length() > 0, "Přijmení must have at least 1 character").bind(OwnerOutDto::getLastName, OwnerOutDto::setLastName);
        binder.forField(email).withValidator(new EmailValidator("Vložte email.")).bind(OwnerOutDto::getEmail, OwnerOutDto::setEmail);
        binder.forField(city).withValidator(name -> name.length() > 0, "City must have at least 1 character").bind(OwnerOutDto::getCity, OwnerOutDto::setCity);
        binder.forField(street).withValidator(name -> name.length() > 0, "Street must have at least 1 character").bind(OwnerOutDto::getStreet, OwnerOutDto::setStreet);
        binder.forField(numberOfHouse).withConverter(new DoubleToIntegerConverter()).bind(OwnerOutDto::getNumberOfHouse, OwnerOutDto::setNumberOfHouse);
        binder.forField(zipCode).withConverter(new DoubleToIntegerConverter()).bind(OwnerOutDto::getZipCode, OwnerOutDto::setZipCode);
        binder.forField(birthDate).bind(OwnerOutDto::getBirthDate, OwnerOutDto::setBirthDate);
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
    }

    private OwnerInDto setOwnerInDto() {
        OwnerInDto ownerInDto = new OwnerInDto();
        ownerInDto.setFirstName(firstName.getValue());
        ownerInDto.setLastName(lastName.getValue());
        ownerInDto.setBirthDate(birthDate.getValue());
        ownerInDto.setEmail(email.getValue());
        ownerInDto.setCity(city.getValue());
        ownerInDto.setStreet(street.getValue());
        ownerInDto.setNumberOfHouse(numberOfHouse.getValue().intValue());
        ownerInDto.setZipCode(zipCode.getValue().intValue());
        return ownerInDto;
    }

    private void setOwnerOutDto() {
        ownerOutDto.setFirstName(firstName.getValue());
        ownerOutDto.setLastName(lastName.getValue());
        ownerOutDto.setBirthDate(birthDate.getValue());
        ownerOutDto.setEmail(email.getValue());
        ownerOutDto.setCity(city.getValue());
        ownerOutDto.setStreet(street.getValue());
        ownerOutDto.setNumberOfHouse(numberOfHouse.getValue().intValue());
    }
}
