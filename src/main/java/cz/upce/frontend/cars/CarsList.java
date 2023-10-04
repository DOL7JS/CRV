package cz.upce.frontend.cars;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteParameters;
import cz.upce.frontend.Menu;
import cz.upce.nnpro_backend.dtos.CarOutDto;
import cz.upce.nnpro_backend.services.CarService;

import javax.annotation.security.PermitAll;

@Route(value = "cars", layout = Menu.class)
@PermitAll
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

        Grid<CarOutDto> gridCars = new Grid<>(CarOutDto.class, false);
        getContent().setHeightFull();
        getContent().setWidthFull();
        gridCars.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        setGridData(gridCars);
        gridCars.addItemClickListener(listener -> {
            if (listener.getClickCount() == 2) {
                gridCars.getUI().ifPresent(ui -> ui.navigate(CarDetail.class, new RouteParameters("carID", listener.getItem().getId().toString())));
            }
        });
        getContent().add(buttonPrimary);
        getContent().add(gridCars);
    }

    private void setGridData(Grid<CarOutDto> grid) {
        grid.addColumn(CarOutDto::getManufacturer).setHeader("Výrobce").setSortable(true).setComparator(item -> item.getManufacturer().toUpperCase());
        grid.addColumn(CarOutDto::getType).setHeader("Model").setSortable(true).setComparator(item -> item.getType().toUpperCase());
        grid.addColumn(CarOutDto::getSPZ).setHeader("SPZ").setSortable(true).setComparator(item -> item.getSPZ() == null ? "" : item.getSPZ().toUpperCase());
        grid.addColumn(CarOutDto::getVin).setHeader("VIN").setSortable(true).setComparator(item -> item.getVin().toUpperCase());
        grid.addColumn(CarOutDto::getColor).setHeader("Barva").setSortable(true).setComparator(item -> item.getColor().toUpperCase());
        grid.addColumn(CarOutDto::getYearOfCreation).setHeader("Datum výroby").setSortable(true);
        grid.addColumn(CarOutDto::getEnginePower).setHeader("Výkon").setSortable(true);
        grid.addComponentColumn(car -> createStatusIcon(car.isSigned()))
                .setHeader("Přihlášeno").setSortable(true).setComparator(item -> item.isSigned() ? "y" : "n");
        grid.addColumn(new ComponentRenderer<>(Button::new, (button, car) -> {
            button.addClickListener(event ->
                    button.getUI().ifPresent(ui -> ui.navigate(RouteConfiguration.forSessionScope()
                            .getUrl(CarAddEdit.class, car.getId()))));
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
