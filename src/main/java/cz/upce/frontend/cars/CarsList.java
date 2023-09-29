package cz.upce.frontend.cars;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import cz.upce.frontend.Menu;
import cz.upce.nnpro_backend.dtos.CarOutDto;
import cz.upce.nnpro_backend.services.CarService;

@Route(value = "cars", layout = Menu.class)
public class CarsList extends Composite<VerticalLayout> {
    private final CarService carService;

    public CarsList(CarService carService) {
        this.carService = carService;
        Button buttonPrimary = new Button();
        buttonPrimary.setText("Přidat auto");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(event -> {
            buttonPrimary.getUI().ifPresent(ui -> ui.navigate(CarAddEdit.class));
        });
        Grid<CarOutDto> stripedGrid = new Grid<>(CarOutDto.class, false);
        getContent().setHeightFull();
        getContent().setWidthFull();
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        setGridSampleData(stripedGrid);
        stripedGrid.addItemClickListener(listener -> {
            if (listener.getClickCount() == 2) {
                stripedGrid.getUI().ifPresent(ui -> ui.navigate(CarDetail.class, new RouteParameters("carID", listener.getItem().getId().toString())));
            }
        });
        getContent().add(buttonPrimary);
        getContent().add(stripedGrid);
    }

    private void setGridSampleData(Grid<CarOutDto> grid) {
        grid.addColumn(CarOutDto::getManufacturer).setHeader("Výrobce");
        grid.addColumn(CarOutDto::getType).setHeader("Model");
        grid.addColumn(CarOutDto::getSPZ).setHeader("SPZ");
        grid.addColumn(CarOutDto::getVin).setHeader("VIN");
        grid.addColumn(CarOutDto::getColor).setHeader("Barva");
        grid.addColumn(CarOutDto::getYearOfCreation).setHeader("Datum výroby");
        grid.addColumn(CarOutDto::getEnginePower).setHeader("Výkon");
        grid.addComponentColumn(car -> createStatusIcon(car.isSigned()))
                .setHeader("Přihlášeno");
        grid.addColumn(new ComponentRenderer<>(Button::new, (button, car) -> {
            button.addClickListener(event -> button.getUI().ifPresent(ui -> ui.navigate(CarAddEdit.class, new RouteParameters("carId", car.getId().toString()))));
            button.setIcon(new Icon(VaadinIcon.EDIT));
        }));
        grid.setItems(carService.getAllCars());
    }


    private Icon createStatusIcon(boolean status) {
        Icon icon;
        if (status) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
            icon.setColor("green");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
            icon.setColor("red");
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

}
