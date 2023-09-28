package cz.upce.frontend.cars;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import cz.upce.frontend.Menu;
import cz.upce.nnpro_backend.dtos.CarOutDto;
import cz.upce.nnpro_backend.entities.Car;
import cz.upce.nnpro_backend.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;

@Route(value = "cars", layout = Menu.class)
public class CarsList extends Composite<VerticalLayout> {
    private final CarService carService;

    public CarsList(CarService carService) {
        this.carService = carService;
        Grid<CarOutDto> stripedGrid = new Grid(CarOutDto.class, false);
        getContent().setHeightFull();
        getContent().setWidthFull();
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        setGridSampleData(stripedGrid);
        stripedGrid.addItemClickListener(listener ->
                {
                    if (listener.getClickCount() == 2) {
                        Notification.show(listener.getItem().getVin());
                        stripedGrid.getUI().ifPresent(ui -> ui.navigate(
                                CarDetail.class,
                                new RouteParameters("carID", listener.getItem().getId().toString())));
                    }
                }
        );
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
        grid.addColumn(CarOutDto::isStolen).setHeader("Přihlášeno");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, car) -> {

                    button.addClickListener(event -> {
                        Notification.show(car.getSPZ());
                    });
                    button.setIcon(new Icon(VaadinIcon.EDIT));
                }));
        grid.setItems(carService.getAllCars());
    }

}
