package cz.upce.frontend;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;

import javax.annotation.security.PermitAll;

@PageTitle("Domovská stránka")
@Route(value = "", layout = Menu.class)
@PermitAll
public class Homepage extends Composite<VerticalLayout> {

    public Homepage() {
        VerticalLayout verticalLayout = new VerticalLayout();
        H1 h1 = new H1();
        getContent().setHeightFull();
        getContent().setWidthFull();
        getContent().setFlexGrow(1.0, verticalLayout);
        verticalLayout.setWidthFull();
        h1.setText("Vítejte na stránkách CRV");
        verticalLayout.setFlexGrow(1.0, h1);
        verticalLayout.setAlignSelf(FlexComponent.Alignment.CENTER, h1);
        StreamResource resource = new StreamResource("car.png", () -> VaadinService.getCurrent().getClassLoader().getResourceAsStream("car.png"));
        verticalLayout.add(h1);
        getContent().add(verticalLayout);

        Image image = new Image(resource, "Alternative Text");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(image);
        getContent().add(horizontalLayout);
        getContent().setFlexGrow(1.0, horizontalLayout);
        horizontalLayout.setWidthFull();

        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }


}
