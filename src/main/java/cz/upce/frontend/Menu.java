package cz.upce.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;
import cz.upce.frontend.office.OfficeListDetail;
import cz.upce.frontend.cars.CarsList;
import cz.upce.frontend.owners.OwnerListDetail;

public class Menu extends AppLayout {
    public Menu() {
        MenuBar menuBar = new MenuBar();
        setMenuBarSampleData(menuBar);
        addToNavbar(menuBar);
    }

    private void setMenuBarSampleData(MenuBar menuBar) {
        menuBar.addItem(new RouterLink("Domů", Homepage.class));
        menuBar.addItem(new RouterLink("Auta", CarsList.class));
        menuBar.addItem(new RouterLink("Stanice", OfficeListDetail.class));
        menuBar.addItem(new RouterLink("Vlastníci", OwnerListDetail.class));
        menuBar.addItem("Zaměstnanci");
    }
}
