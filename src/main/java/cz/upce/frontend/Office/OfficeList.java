package cz.upce.frontend.Office;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import cz.upce.frontend.Menu;
import cz.upce.nnpro_backend.dtos.CarOutDto;
import cz.upce.nnpro_backend.entities.BranchOffice;
import cz.upce.nnpro_backend.services.BranchOfficeService;
import cz.upce.nnpro_backend.services.CarService;

@Route(value = "offices", layout = Menu.class)
public class OfficeList extends Composite<VerticalLayout> {
    private final BranchOfficeService officeList;

    public OfficeList(BranchOfficeService officeList) {
        this.officeList = officeList;
        Grid<BranchOffice> stripedGrid = new Grid(BranchOffice.class, false);
        getContent().setHeightFull();
        getContent().setWidthFull();
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        setGridSampleData(stripedGrid);
        getContent().add(stripedGrid);
    }

    private void setGridSampleData(Grid<BranchOffice> grid) {
        grid.addColumn(BranchOffice::getRegion).setHeader("Kraj");
        grid.addColumn(BranchOffice::getDistrict).setHeader("Okres");
        grid.addColumn(BranchOffice::getCity).setHeader("Město");
        grid.setItems(officeList.getAllOffices());
    }

}
