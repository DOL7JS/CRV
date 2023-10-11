package cz.upce.frontend.cars;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import cz.upce.frontend.FieldValidator;
import cz.upce.frontend.Menu;
import cz.upce.frontend.errorHandler.ErrorView;
import cz.upce.nnpro_backend.dtos.CarInDto;
import cz.upce.nnpro_backend.dtos.CarOutDto;
import cz.upce.nnpro_backend.services.CarService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.NoSuchElementException;

@Route(value = "cars/edit", layout = Menu.class)
@PermitAll
@RolesAllowed({"ROLE_Admin", "ROLE_Okres"})
public class CarAddEdit extends VerticalLayout implements HasUrlParameter<Long> {
    CarOutDto car;
    TextField textFieldVIN = new TextField();
    TextField textFieldManufacturer = new TextField();
    TextField textFieldColor = new TextField();
    NumberField numberFieldEmissionStandard = new NumberField();
    DatePicker datePickerCreationYear = new DatePicker();
    TextField textFieldModel = new TextField();
    NumberField numberFieldEnginePower = new NumberField();
    NumberField textFieldTorque = new NumberField();
    Checkbox checkboxIsStolen = new Checkbox();
    Checkbox checkboxIsInDeposit = new Checkbox();
    Binder<CarInDto> binder;
    private final CarService carService;


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
        if (parameter != null) {
            try {
                car = carService.getCar(parameter);
            } catch (NoSuchElementException ex) {
                event.forwardTo(ErrorView.class);
                return;
            }
            SetCarDetail(car);
        } else {
            checkboxIsInDeposit.setEnabled(false);
            checkboxIsStolen.setEnabled(false);
        }
    }


    CarAddEdit(CarService carService) {
        this.carService = carService;
        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();
        VerticalLayout verticalLayoutEmptyLeft = new VerticalLayout();
        VerticalLayout verticalLayoutEmptyRight = new VerticalLayout();

        VerticalLayout layoutMiddle = new VerticalLayout();
        HorizontalLayout horizontalLayoutMiddle = new HorizontalLayout();
        VerticalLayout verticalLayoutLeftColumnFields = new VerticalLayout();
        VerticalLayout verticalLayoutRightColumnFields = new VerticalLayout();
        HorizontalLayout horizontalLayoutButton = new HorizontalLayout();

        Button buttonSave = new Button();
        H3 h3 = new H3();


        setWidthFull();
        addClassName(Padding.LARGE);
        setHeightFull();
        horizontalLayoutMain.setWidthFull();
        setFlexGrow(1.0, horizontalLayoutMain);
        horizontalLayoutMain.setFlexGrow(1.0, verticalLayoutEmptyLeft);
        horizontalLayoutMain.setFlexGrow(1.0, layoutMiddle);
        layoutMiddle.addClassName(Padding.XSMALL);

        checkboxIsStolen.setLabel("Kradené?");
        checkboxIsInDeposit.setLabel("V depositu?");
        h3.setText("Informace o autě");
        textFieldManufacturer.setLabel("Výrobce");
        numberFieldEmissionStandard.setLabel("Emisní standard");
        datePickerCreationYear.setLabel("Datum výroby");
        numberFieldEnginePower.setLabel("Výkon");
        textFieldModel.setLabel("Model");
        textFieldTorque.setLabel("Točivý moment");
        buttonSave.setText("Uložit");
        textFieldColor.setLabel("Barva");
        textFieldVIN.setLabel("VIN");

        horizontalLayoutMiddle.addClassName(Gap.LARGE);
        layoutMiddle.setFlexGrow(1.0, horizontalLayoutMiddle);
        horizontalLayoutMiddle.setFlexGrow(1.0, verticalLayoutLeftColumnFields);
        horizontalLayoutMain.setFlexGrow(1.0, verticalLayoutEmptyRight);
        horizontalLayoutMiddle.setFlexGrow(1.0, verticalLayoutRightColumnFields);

        layoutMiddle.setWidth(null);
        verticalLayoutEmptyLeft.setWidth(null);
        verticalLayoutLeftColumnFields.setWidth(null);
        verticalLayoutRightColumnFields.setWidth(null);
        verticalLayoutEmptyRight.setWidth(null);
        horizontalLayoutMiddle.setWidthFull();
        textFieldVIN.setWidthFull();
        textFieldColor.setWidthFull();
        datePickerCreationYear.setWidthFull();
        textFieldManufacturer.setWidthFull();
        numberFieldEmissionStandard.setWidthFull();
        numberFieldEnginePower.setWidthFull();
        textFieldTorque.setWidthFull();
        textFieldModel.setWidthFull();
        horizontalLayoutButton.addClassName(Gap.MEDIUM);
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonSave.addClickListener(event -> {
            try {
                SaveCar();
            } catch (Exception e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);

                throw new RuntimeException(e);
            }
        });

        binder = new Binder<>(CarInDto.class);
        binder.forField(textFieldVIN).withValidator(vin -> vin.length() == 17, "VIN must have 17 characters").bind(CarInDto::getVin, CarInDto::setVin);
        binder.forField(textFieldManufacturer).withValidator(name -> name.length() > 1, "Name must have at least 2 characters").bind(CarInDto::getVin, CarInDto::setVin);
        binder.forField(textFieldModel).withValidator(name -> name.length() > 1, "Name must have at least 2 characters").bind(CarInDto::getVin, CarInDto::setVin);
        binder.forField(textFieldColor).withValidator(name -> name.length() > 1, "Color must have at least 2 characters").bind(CarInDto::getVin, CarInDto::setVin);


        add(horizontalLayoutMain);
        horizontalLayoutMain.add(verticalLayoutEmptyLeft);
        horizontalLayoutMain.add(layoutMiddle);
        layoutMiddle.add(h3);
        layoutMiddle.add(horizontalLayoutMiddle);
        horizontalLayoutMiddle.add(verticalLayoutLeftColumnFields);
        verticalLayoutLeftColumnFields.add(textFieldVIN);
        verticalLayoutLeftColumnFields.add(textFieldManufacturer);
        verticalLayoutLeftColumnFields.add(textFieldColor);
        verticalLayoutLeftColumnFields.add(numberFieldEmissionStandard);
        verticalLayoutLeftColumnFields.add(checkboxIsStolen);
        horizontalLayoutMiddle.add(verticalLayoutRightColumnFields);
        verticalLayoutRightColumnFields.add(datePickerCreationYear);
        verticalLayoutRightColumnFields.add(textFieldModel);
        verticalLayoutRightColumnFields.add(numberFieldEnginePower);
        verticalLayoutRightColumnFields.add(textFieldTorque);
        verticalLayoutRightColumnFields.add(checkboxIsInDeposit);
        layoutMiddle.add(horizontalLayoutButton);
        horizontalLayoutButton.add(buttonSave);
        horizontalLayoutMain.add(verticalLayoutEmptyRight);

    }

    private void SetCarDetail(CarOutDto car) {
        textFieldVIN.setValue(car.getVin());
        textFieldManufacturer.setValue(car.getManufacturer());
        textFieldTorque.setValue(car.getTorque());
        textFieldColor.setValue(car.getColor());
        textFieldModel.setValue(car.getType());
        datePickerCreationYear.setValue(car.getYearOfCreation());
        numberFieldEnginePower.setValue(car.getEnginePower());
        numberFieldEmissionStandard.setValue(car.getEmissionStandard());
        checkboxIsStolen.setValue(car.isStolen());
        checkboxIsInDeposit.setValue(car.isInDeposit());
    }

    private void SaveCar() {
        boolean valid = !FieldValidator.validateEmptyField(textFieldVIN) & !FieldValidator.validateEmptyField(textFieldManufacturer) &
                !FieldValidator.validateEmptyField(textFieldModel) & !FieldValidator.validateEmptyField(textFieldTorque) &
                !FieldValidator.validateEmptyField(numberFieldEmissionStandard) & !FieldValidator.validateEmptyField(numberFieldEnginePower) &
                !FieldValidator.validateEmptyField(textFieldColor) & !FieldValidator.validateEmptyField(datePickerCreationYear);

        if (valid && binder.isValid()) {
            CarInDto carInDto = new CarInDto();
            carInDto.setVin(textFieldVIN.getValue());
            carInDto.setManufacturer(textFieldManufacturer.getValue());
            carInDto.setType(textFieldModel.getValue());
            carInDto.setTorque(textFieldTorque.getValue());
            carInDto.setEmissionStandard(numberFieldEmissionStandard.getValue());
            carInDto.setEnginePower(numberFieldEnginePower.getValue());
            carInDto.setYearOfCreation(datePickerCreationYear.getValue());
            carInDto.setColor(textFieldColor.getValue());
            carInDto.setInDeposit(checkboxIsInDeposit.getValue());
            carInDto.setStolen(checkboxIsStolen.getValue());
            Long id;
            if (car == null) {
                id = carService.addCar(carInDto).getId();
                Notification.show("Auto úspěšně přidáno.", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {
                id = car.getId();
                carService.editCar(car.getId(), carInDto);

                Notification.show("Auto úspěšně upraveno.", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
            getUI().ifPresent(ui -> ui.navigate(CarDetail.class, new RouteParameters("carID", String.valueOf(id))));

        }

    }


}
