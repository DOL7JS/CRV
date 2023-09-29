package cz.upce.frontend.cars;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import cz.upce.frontend.FieldValidator;
import cz.upce.frontend.Menu;
import cz.upce.nnpro_backend.dtos.CarOutDto;
import cz.upce.nnpro_backend.dtos.OwnerDto;
import cz.upce.nnpro_backend.dtos.OwnerInDto;
import cz.upce.nnpro_backend.dtos.OwnerOutDto;
import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.services.CarService;
import cz.upce.nnpro_backend.services.ConversionService;
import cz.upce.nnpro_backend.services.OwnerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cz.upce.frontend.FieldValidator.validateEmptyField;

@Route(value = "cars/:carID", layout = Menu.class)
public class CarDetail extends VerticalLayout implements BeforeEnterObserver {

    CarOutDto car;
    private final CarService carService;
    private final OwnerService ownerService;
    H3 h3SPZ = new H3();
    H3 h3VIN = new H3();
    H3 h3Manufacturer = new H3();
    H3 h3CreationDate = new H3();
    H3 h3Model = new H3();
    H3 h3Color = new H3();
    H3 h3EnginePower = new H3();
    H3 h3EmissionStandard = new H3();
    H3 h3Torque = new H3();
    H3 h3Stolen = new H3();
    H3 h3Deposit = new H3();
    Grid<OwnerDto> stripedGrid = new Grid<>(OwnerDto.class, false);


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String id = event.getRouteParameters().get("carID").orElse("-1");
        if (!id.equals("-1")) {
            car = carService.getCar(Long.valueOf(id));
            SetCarDetail(car);
            SetCarOwners(car);
        }
        Notification.show("ID: " + id);
    }

    private void SetCarOwners(CarOutDto car) {
        stripedGrid.removeAllColumns();
        stripedGrid.addColumn(OwnerDto::getFirstName).setHeader("Jméno");
        stripedGrid.addColumn(OwnerDto::getLastName).setHeader("Příjmení");
        stripedGrid.addColumn(OwnerDto::getStartOfSignUp).setHeader("Přihlášení");
        Grid.Column<OwnerDto> columnSignOut = stripedGrid.addColumn(OwnerDto::getEndOfSignUp).setHeader("Odhlášení");
        if (car != null) {
            stripedGrid.setItems(car.getOwners());
        }
        GridSortOrder<OwnerDto> sort = new GridSortOrder<>(columnSignOut, SortDirection.DESCENDING);
        stripedGrid.sort(List.of(sort));
    }

    private void SetCarDetail(CarOutDto car) {
        h3SPZ.setText(car.getSPZ() == null ? "-" : car.getSPZ());
        h3Manufacturer.setText(car.getManufacturer());
        h3Model.setText(car.getType());
        h3Color.setText(car.getColor());
        h3VIN.setText(car.getVin());
        h3CreationDate.setText(car.getYearOfCreation().toString());
        h3EnginePower.setText(String.valueOf(car.getEnginePower()));
        h3EmissionStandard.setText(String.valueOf(car.getEmissionStandard()));
        h3Torque.setText(String.valueOf(car.getTorque()));
        h3Stolen.setText(car.isStolen() ? "Ano" : "Ne");
        h3Deposit.setText(car.isInDeposit() ? "Ano" : "Ne");

    }


    CarDetail(CarService carService, OwnerService ownerService) {
        this.carService = carService;
        this.ownerService = ownerService;
        HorizontalLayout mainHorizontalLayout = new HorizontalLayout();
        VerticalLayout leftEmptyLayout = new VerticalLayout();
        VerticalLayout rightLayout = new VerticalLayout();
        VerticalLayout middleLayout = new VerticalLayout();
        HorizontalLayout detailCarLayout = new HorizontalLayout();
        H1 h1CarDetail = new H1();
        h1CarDetail.getStyle().set("margin-top", "0");
        Button buttonEditCar = new Button();
        buttonEditCar.getStyle().set("display", "flex").set("justify-content", "center").set("align-items", "center");
        h3SPZ.getStyle().set("margin-top", "0");
        h3VIN.getStyle().set("margin-top", "0");
        h3Manufacturer.getStyle().set("margin-top", "0");
        h3CreationDate.getStyle().set("margin-top", "0");
        h3Model.getStyle().set("margin-top", "0");
        h3Color.getStyle().set("margin-top", "0");
        h3EnginePower.getStyle().set("margin-top", "0");
        h3EmissionStandard.getStyle().set("margin-top", "0");
        h3Torque.getStyle().set("margin-top", "0");
        h3Stolen.getStyle().set("margin-top", "0");
        h3Deposit.getStyle().set("margin-top", "0");
        H2 h2SPZ = new H2();
        h2SPZ.getStyle().set("margin-bottom", "0");
        Hr hr = new Hr();
        H2 h2VIN = new H2();
        h2VIN.getStyle().set("margin-bottom", "0");
        Hr hr2 = new Hr();
        H2 h2Manufacturer = new H2();
        h2Manufacturer.getStyle().set("margin-bottom", "0");
        Hr hr3 = new Hr();
        H2 h2Model = new H2();
        h2Model.getStyle().set("margin-bottom", "0");
        Hr hr4 = new Hr();
        H2 h2CreationDate = new H2();
        h2CreationDate.getStyle().set("margin-bottom", "0");
        Hr hr5 = new Hr();
        H2 h2Color = new H2();
        h2Color.getStyle().set("margin-bottom", "0");
        Hr hr6 = new Hr();
        H2 h2EnginePower = new H2();
        h2EnginePower.getStyle().set("margin-bottom", "0");
        Hr hr7 = new Hr();
        H2 h2EmissionStandard = new H2();
        h2EmissionStandard.getStyle().set("margin-bottom", "0");
        Hr hr8 = new Hr();
        H2 h2Torque = new H2();
        h2Torque.getStyle().set("margin-bottom", "0");
        Hr hr9 = new Hr();
        H2 h2Stolen = new H2();
        h2Stolen.getStyle().set("margin-bottom", "0");
        Hr hr10 = new Hr();
        H2 h2Deposit = new H2();
        h2Deposit.getStyle().set("margin-bottom", "0");
        Hr hr11 = new Hr();
        Hr hr12 = new Hr();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        H2 h2Owners = new H2();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Button buttonSignIn = new Button();
        Button buttonSignOut = new Button();
        buttonSignOut.addClickListener(event -> {
            signOutCar();

        });
//        Button buttonOverrideOwner = new Button();
        buttonSignIn.addClickListener(event -> {
            openSignInDialog();
        });
        setHeightFull();
        setWidthFull();
        setFlexGrow(1.0, mainHorizontalLayout);
        mainHorizontalLayout.setWidthFull();
        mainHorizontalLayout.addClassName(Gap.MEDIUM);
        leftEmptyLayout.setHeightFull();
        leftEmptyLayout.setWidth(null);
        mainHorizontalLayout.setFlexGrow(1.0, rightLayout);
        rightLayout.setHeightFull();
        rightLayout.setWidth(null);
        middleLayout.setWidthFull();
        rightLayout.setFlexGrow(1.0, middleLayout);
        detailCarLayout.setWidthFull();
        middleLayout.setFlexGrow(1.0, detailCarLayout);
        detailCarLayout.addClassName(Gap.MEDIUM);
        h1CarDetail.setText("Detail auta");
        buttonEditCar.setText("Upravit");
        buttonEditCar.addClickListener(event -> {
            buttonEditCar.getUI().ifPresent(ui -> ui.navigate(RouteConfiguration.forSessionScope()
                    .getUrl(CarAddEdit.class, car.getId())));
        });
        buttonEditCar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        h2SPZ.setText("SPZ");
        h2VIN.setText("VIN");
        h2Manufacturer.setText("Výrobce");
        h2Model.setText("Model");
        h2CreationDate.setText("Datum výroby");
        h2Color.setText("Barva");
        h2EnginePower.setText("Výkon");
        h2EmissionStandard.setText("Emisní standard");
        h2Torque.setText("Točivý moment");
        h2Stolen.setText("Je kradené?");
        h2Deposit.setText("Je v depositu?");
        mainHorizontalLayout.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setHeightFull();
        layoutColumn4.setWidth(null);
        h2Owners.setText("Vlastníci");
        layoutRow3.addClassName(Gap.MEDIUM);
        buttonSignIn.setText("Přihlásit");
        buttonSignIn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSignOut.setText("Odhlásit");
        buttonSignOut.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(mainHorizontalLayout);
        mainHorizontalLayout.add(leftEmptyLayout);
        mainHorizontalLayout.add(rightLayout);
        rightLayout.add(middleLayout);
        middleLayout.add(detailCarLayout);
        detailCarLayout.add(h1CarDetail);
        detailCarLayout.add(buttonEditCar);
        middleLayout.add(h2SPZ);
        middleLayout.add(h3SPZ);
        middleLayout.add(hr);
        middleLayout.add(h2VIN);
        middleLayout.add(h3VIN);
        middleLayout.add(hr2);
        middleLayout.add(h2Manufacturer);
        middleLayout.add(h3Manufacturer);
        middleLayout.add(hr3);
        middleLayout.add(h2Model);
        middleLayout.add(h3Model);
        middleLayout.add(hr4);
        middleLayout.add(h2CreationDate);
        middleLayout.add(h3CreationDate);
        middleLayout.add(hr5);
        middleLayout.add(h2Color);
        middleLayout.add(h3Color);
        middleLayout.add(hr6);
        middleLayout.add(h2EnginePower);
        middleLayout.add(h3EnginePower);
        middleLayout.add(hr7);
        middleLayout.add(h2EmissionStandard);
        middleLayout.add(h3EmissionStandard);
        middleLayout.add(hr8);
        middleLayout.add(h2Torque);
        middleLayout.add(h3Torque);
        middleLayout.add(hr9);
        middleLayout.add(h2Stolen);
        middleLayout.add(h3Stolen);
        middleLayout.add(hr10);
        middleLayout.add(h2Deposit);
        middleLayout.add(h3Deposit);
        middleLayout.add(hr11);
        middleLayout.add(hr12);
        mainHorizontalLayout.add(layoutColumn4);
        layoutColumn4.add(h2Owners);
        layoutColumn4.add(layoutRow3);
        layoutRow3.add(buttonSignIn);
        layoutRow3.add(buttonSignOut);
        layoutColumn4.add(stripedGrid);

    }

    private void signOutCar() {
        CarOutDto carOutDto = carService.signOutCar(car.getId());
        if (carOutDto != null) {
            car = carOutDto;
            stripedGrid.setItems(car.getOwners());
            Notification.show("Auto bylo odhlášeno", 2000, Notification.Position.MIDDLE);
        } else {
            Notification.show("Nelze odhlásit nepříhlášené auto", 2000, Notification.Position.MIDDLE);
        }

    }

    private void openSignInDialog() {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Přihlásit auto");

        VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Přihlásit auto");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        TextField firstNameField = new TextField("Jméno");
        firstNameField.setRequired(true);
        firstNameField.setRequiredIndicatorVisible(true);
        TextField lastNameField = new TextField("Příjmení");
        lastNameField.setRequired(true);
        EmailField emailField = new EmailField("Email");
        emailField.setRequiredIndicatorVisible(true);
        DatePicker dateField = new DatePicker("Datum narození");
        dateField.setRequired(true);
        TextField cityField = new TextField("Město");
        cityField.setRequired(true);
        TextField streetField = new TextField("Ulice");
        streetField.setRequired(true);
        NumberField houseNumberField = new NumberField("Číslo popisné");
        houseNumberField.setRequiredIndicatorVisible(true);
        NumberField zipCodeField = new NumberField("PSČ");
        zipCodeField.setRequiredIndicatorVisible(true);
        zipCodeField.setMax(5);
        Binder<OwnerDto> binder = new Binder<>(OwnerDto.class);
        binder.forField(firstNameField).withValidator(name -> name.length() > 1, "Name must have at least 2 characters").bind(OwnerDto::getFirstName, OwnerDto::setFirstName);
        binder.forField(lastNameField).withValidator(name -> name.length() > 1, "Name must have at least 2 characters").bind(OwnerDto::getLastName, OwnerDto::setLastName);
        binder.forField(emailField).withValidator(new EmailValidator("This doesn't look like a valid email address")).bind(OwnerDto::getEmail, OwnerDto::setEmail);
        binder.forField(cityField).withValidator(city -> city.length() > 1, "City must have at least 2 characters").bind(OwnerDto::getCity, OwnerDto::setCity);
        binder.forField(streetField).withValidator(street -> street.length() > 1, "Street must have at least 2 characters").bind(OwnerDto::getStreet, OwnerDto::setStreet);

        VerticalLayout fieldLayout = new VerticalLayout(firstNameField,
                lastNameField, emailField, dateField, cityField, streetField, houseNumberField, zipCodeField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Zrušit", e -> {
            dialog.close();
        });
        Button saveButton = new Button("Uložit", e -> {
            boolean valid = !FieldValidator.validateEmptyField(firstNameField) & !FieldValidator.validateEmptyField(lastNameField) & !FieldValidator.validateEmptyField(cityField)
                    & !FieldValidator.validateEmptyField(streetField) & !FieldValidator.validateEmptyField(houseNumberField) & !FieldValidator.validateEmptyField(zipCodeField) & !FieldValidator.validateEmptyField(emailField);

            if (dateField.getValue() == null) {
                dateField.setInvalid(true);
                valid = false;
            }
            if (binder.isValid() && valid) {
                OwnerInDto ownerInDto = new OwnerInDto();
                ownerInDto.setFirstName(firstNameField.getValue());
                ownerInDto.setLastName(lastNameField.getValue());
                ownerInDto.setEmail(emailField.getValue());
                ownerInDto.setBirthDate(dateField.getValue());
                ownerInDto.setCity(cityField.getValue());
                ownerInDto.setStreet(streetField.getValue());
                ownerInDto.setNumberOfHouse(houseNumberField.getValue().intValue());
                ownerInDto.setZipCode(zipCodeField.getValue().intValue());
                try {
                    car = ConversionService.convertToCarDetailOutDto(carService.signInCar(car.getId(), ownerInDto));
                    h3SPZ.setText(car.getSPZ());
                    stripedGrid.setItems(car.getOwners());
                    //SetCarOwners(car);
                } catch (Exception ex) {
                    Notification.show(ex.getMessage(), 3000, Notification.Position.TOP_CENTER);
                    throw new RuntimeException(ex);
                }
                dialog.close();
            }
        });
        Button ownerButton = new Button("Zvolit existujícího vlastníka", e -> {
            dialog.close();
            Dialog dialogOwner = new Dialog();
            dialogOwner.getElement().setAttribute("aria-label", "Přihlásit auto");

            VerticalLayout dialogLayout = createDialogOwnerLayout(dialogOwner);
            dialogOwner.add(dialogLayout);
            dialogOwner.open();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(ownerButton, cancelButton,
                saveButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

    private VerticalLayout createDialogOwnerLayout(Dialog dialogOwner) {
        H2 headline = new H2("Přihlásit auto");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        ComboBox<OwnerOutDto> ownerComboBox = new ComboBox("Vlastník");
        ownerComboBox.setItems(ownerService.getAllOwners());
        ownerComboBox.setItemLabelGenerator(item -> item.getFirstName() + " " + item.getLastName() + ", " + item.getBirthDate().toString());
        Button cancelButton = new Button("Zrušit", e -> {
            dialogOwner.close();
        });
        Button saveButton = new Button("Uložit", e -> {
            if (!ownerComboBox.isEmpty()) {
                try {
                    car = ConversionService.convertToCarDetailOutDto(carService.changeOwner(car.getId(), ownerComboBox.getValue().getId()));
                    stripedGrid.setItems(car.getOwners());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                dialogOwner.close();

            }
        });
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        VerticalLayout fieldLayout = new VerticalLayout(ownerComboBox);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }


}
