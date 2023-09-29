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
import cz.upce.nnpro_backend.dtos.CarInDto;
import cz.upce.nnpro_backend.dtos.CarOutDto;
import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.services.CarService;
import cz.upce.nnpro_backend.services.ConversionService;

@Route(value = "cars/edit", layout = Menu.class)
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
            car = carService.getCar(parameter);
            SetCarDetail(car);
        } else {
            checkboxIsInDeposit.setEnabled(false);
            checkboxIsStolen.setEnabled(false);
        }
        Notification.show("ID: " + parameter);
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


    CarAddEdit(CarService carService) {
        this.carService = carService;
        HorizontalLayout layoutMain = new HorizontalLayout();
        VerticalLayout layoutColumn5 = new VerticalLayout();
        VerticalLayout layoutMiddle = new VerticalLayout();
        H3 h3 = new H3();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        Button buttonSave = new Button();

        checkboxIsStolen.setLabel("Kradené?");
        checkboxIsInDeposit.setLabel("V depositu?");
        VerticalLayout layoutColumn6 = new VerticalLayout();
        setWidthFull();
        addClassName(Padding.LARGE);
        setHeightFull();
        layoutMain.setWidthFull();
        setFlexGrow(1.0, layoutMain);
        layoutMain.setFlexGrow(1.0, layoutColumn5);
        layoutColumn5.setWidth(null);
        layoutMain.setFlexGrow(1.0, layoutMiddle);
        layoutMiddle.addClassName(Padding.XSMALL);
        layoutMiddle.setWidth(null);
        h3.setText("Informace o autě");
        layoutRow2.setWidthFull();
        layoutRow2.addClassName(Gap.LARGE);
        layoutMiddle.setFlexGrow(1.0, layoutRow2);
        layoutRow2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth(null);
        textFieldVIN.setLabel("VIN");
        textFieldVIN.setWidthFull();
        textFieldManufacturer.setLabel("Výrobce");
        textFieldManufacturer.setWidthFull();
        textFieldColor.setLabel("Barva");
        textFieldColor.setWidthFull();
        numberFieldEmissionStandard.setLabel("Emisní standard");
        numberFieldEmissionStandard.setWidthFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setWidth(null);
        datePickerCreationYear.setLabel("Datum výroby");
        datePickerCreationYear.setWidthFull();
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidthFull();
        textFieldModel.setLabel("Model");
        layoutRow3.setFlexGrow(1.0, textFieldModel);
        numberFieldEnginePower.setLabel("Výkon");
        numberFieldEnginePower.setWidthFull();
        textFieldTorque.setLabel("Točivý moment");
        textFieldTorque.setWidthFull();
        layoutRow4.addClassName(Gap.MEDIUM);
        buttonSave.setText("Uložit");
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


        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutMain.setFlexGrow(1.0, layoutColumn6);
        layoutColumn6.setWidth(null);
        add(layoutMain);
        layoutMain.add(layoutColumn5);
        layoutMain.add(layoutMiddle);
        layoutMiddle.add(h3);
        layoutMiddle.add(layoutRow2);
        layoutRow2.add(layoutColumn3);
        layoutColumn3.add(textFieldVIN);
        layoutColumn3.add(textFieldManufacturer);
        layoutColumn3.add(textFieldColor);
        layoutColumn3.add(numberFieldEmissionStandard);
        layoutColumn3.add(checkboxIsStolen);
        layoutRow2.add(layoutColumn4);
        layoutColumn4.add(datePickerCreationYear);
        layoutColumn4.add(layoutRow3);
        layoutRow3.add(textFieldModel);
        layoutColumn4.add(numberFieldEnginePower);
        layoutColumn4.add(textFieldTorque);
        layoutColumn4.add(checkboxIsInDeposit);
        layoutMiddle.add(layoutRow4);
        layoutRow4.add(buttonSave);
        layoutMain.add(layoutColumn6);

    }

    private void SaveCar() throws Exception {
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
