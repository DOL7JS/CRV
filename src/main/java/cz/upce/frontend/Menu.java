package cz.upce.frontend;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import cz.upce.frontend.Office.OfficeList;
import cz.upce.frontend.cars.CarsList;

public class Menu extends AppLayout {
    public Menu() {
        MenuBar menuBar = new MenuBar();
        setMenuBarSampleData(menuBar);
        addToNavbar(menuBar);
    }

    private void setMenuBarSampleData(MenuBar menuBar) {
        menuBar.addItem(new RouterLink("Domů", Homepage.class));
        menuBar.addItem(new RouterLink("Auta", CarsList.class));
        menuBar.addItem(new RouterLink("Stanice", OfficeList.class));
        menuBar.addItem("Zaměstnanci");
    }
}
