package cz.upce.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import cz.upce.frontend.office.OfficeListDetail;
import cz.upce.frontend.cars.CarsList;
import cz.upce.frontend.owners.OwnerListDetail;
import cz.upce.frontend.users.UserDetail;
import cz.upce.frontend.users.UserList;

public class Menu extends AppLayout {
    public Menu() {
        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();
        VerticalLayout verticalLayoutLeft = new VerticalLayout();
        VerticalLayout verticalLayoutMiddle = new VerticalLayout();
        VerticalLayout verticalLayoutRight = new VerticalLayout();
        horizontalLayoutMain.setWidthFull();
        horizontalLayoutMain.addClassNames(LumoUtility.Gap.MEDIUM);
        verticalLayoutLeft.setWidth(null);
        verticalLayoutRight.setWidth(null);
        verticalLayoutMiddle.setWidth(null);
        horizontalLayoutMain.setFlexGrow(1.0, verticalLayoutMiddle);
        MenuBar menuBar = new MenuBar();
        setMenuBarData(menuBar);
        verticalLayoutLeft.add(menuBar);


        Button avatar = new Button("DOL7JS");
        avatar.addClickListener(event -> {
            avatar.getUI().ifPresent(ui -> ui.navigate(String.format("users/edit/%s", "1")));
        });
        verticalLayoutRight.add(avatar);

        horizontalLayoutMain.add(verticalLayoutLeft, verticalLayoutMiddle, verticalLayoutRight);
        addToNavbar(horizontalLayoutMain);
    }

    private void setMenuBarData(MenuBar menuBar) {
        menuBar.addItem("Domů", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(Homepage.class)));
        menuBar.addItem("Auta", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(CarsList.class)));
        menuBar.addItem("Stanice", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(OfficeListDetail.class)));
        menuBar.addItem("Vlastníci", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(OwnerListDetail.class)));
        menuBar.addItem("Uživatelé", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(UserList.class)));
    }
}
