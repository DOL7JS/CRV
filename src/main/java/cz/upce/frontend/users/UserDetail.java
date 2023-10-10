package cz.upce.frontend.users;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import cz.upce.frontend.FieldValidator;
import cz.upce.frontend.Menu;
import cz.upce.frontend.office.OfficeListDetail;
import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.dtos.NewPasswordDto;
import cz.upce.nnpro_backend.dtos.UserInDto;
import cz.upce.nnpro_backend.dtos.UserOutDto;
import cz.upce.nnpro_backend.entities.Role;
import cz.upce.nnpro_backend.security.SecurityService;
import cz.upce.nnpro_backend.services.BranchOfficeService;
import cz.upce.nnpro_backend.services.RoleService;
import cz.upce.nnpro_backend.services.UserService;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;

@Route(value = "users/edit/:userID?", layout = Menu.class)
@PermitAll
public class UserDetail extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final String USER_ID = "userID";
    private final UserService userService;
    private final BranchOfficeService branchOfficeService;
    private final RoleService roleService;
    private UserOutDto userOutDto;
    private final SecurityService securityService;

    TextField textFieldUsername;
    EmailField emailField;
    PasswordField passwordField;
    TextField textFieldJobPosition;
    ComboBox<BranchOfficeDto> comboBoxOffice;
    ComboBox<Role> comboBoxRole;
    Button buttonSave;
    Button buttonChangePassword;
    VerticalLayout verticalLayoutInMiddle;
    HorizontalLayout horizontalLayoutButtons;
    PasswordField oldPassword;
    PasswordField newPassword;
    HorizontalLayout horizontalLayoutMain;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {


        Optional<Long> userId = event.getRouteParameters().get(USER_ID).map(Long::parseLong);
        if (userId.isPresent()) {
//            if (securityService.isKrajOfficer() && securityService.getAuthenticatedUser().getId().equals(userId.get())) {
//                comboBoxRole.setEnabled(false);
//            } else
            Optional<UserOutDto> user = Optional.ofNullable(userService.getUser(userId.get()));


            if (user.isPresent()) {
                if (!user.get().getId().equals(securityService.getAuthenticatedUser().getId())) {
                    horizontalLayoutButtons.remove(buttonChangePassword);
                } else if (!horizontalLayoutButtons.isAttached()) {
                    horizontalLayoutButtons.add(buttonChangePassword);
                }

                if (!securityService.isAdmin() && user.get().getRole().getName().equals("ROLE_Admin")) {
                    horizontalLayoutMain.removeAll();
                    Notification.show("Nemáte oprávnění", 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                } else if (securityService.isKrajOfficer()) {
                    comboBoxRole.setItems(roleService.getOfficeRoles());
                    comboBoxOffice.setItems(branchOfficeService.getOfficesByRegion(securityService.getAuthenticatedUser().getBranchOffice()));
                    if (!user.get().getBranchOfficeDto().getRegion().equals(securityService.getAuthenticatedUser().getBranchOffice().getRegion())
                            && !securityService.getAuthenticatedUser().getId().equals(userId.get())) {
                        horizontalLayoutMain.removeAll();
                        Notification.show("Nemáte oprávnění", 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                        return;
                    }
                } else if (securityService.isOkresOfficer() && securityService.getAuthenticatedUser().getId().equals(userId.get())) {
                    textFieldJobPosition.setEnabled(false);
                    comboBoxOffice.setEnabled(false);
                    comboBoxRole.setEnabled(false);
                } else if (securityService.isOkresOfficer() && !securityService.getAuthenticatedUser().getId().equals(userId.get())) {
                    horizontalLayoutMain.removeAll();
                    Notification.show("Nemáte oprávnění", 5000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }


                verticalLayoutInMiddle.remove(passwordField);
                fillFields(user.get());
            } else {
                Notification.show(
                        String.format("The requested user was not found, ID = %s", userId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                event.forwardTo(OfficeListDetail.class);
            }
        } else {
            if (securityService.isKrajOfficer()) {
                comboBoxOffice.setItems(branchOfficeService.getOfficesByRegion(securityService.getAuthenticatedUser().getBranchOffice()));
            }
            binder.forField(passwordField).withValidator(password -> password.length() > 7, "Password must have at least 8 characters").bind(UserInDto::getPassword, UserInDto::setPassword);
            horizontalLayoutButtons.remove(buttonChangePassword);

        }
    }

    public UserDetail(UserService userService, BranchOfficeService branchOfficeService, RoleService roleService, SecurityService securityService) {
        this.userService = userService;
        this.branchOfficeService = branchOfficeService;
        this.roleService = roleService;
        this.securityService = securityService;
        horizontalLayoutMain = new HorizontalLayout();
        VerticalLayout verticalLayoutEmptyLeft = new VerticalLayout();
        VerticalLayout verticalLayoutMiddle = new VerticalLayout();
        H3 h3 = new H3();
        HorizontalLayout horizontalLayoutMiddle = new HorizontalLayout();
        verticalLayoutInMiddle = new VerticalLayout();
        horizontalLayoutButtons = new HorizontalLayout();
        VerticalLayout verticalLayoutEmptyRight = new VerticalLayout();

        textFieldUsername = new TextField();
        passwordField = new PasswordField();
        emailField = new EmailField();
        textFieldJobPosition = new TextField();
        comboBoxOffice = new ComboBox<>();
        comboBoxRole = new ComboBox<>();
        buttonSave = new Button();
        buttonChangePassword = new Button();

        buttonSave.addClickListener(event -> {
            if (userOutDto == null) {
                addUser();
            } else {
                editUser();
            }

        });
        getContent().setWidthFull();
        getContent().addClassName(Padding.LARGE);
        horizontalLayoutMain.setWidthFull();
        horizontalLayoutMain.setFlexGrow(1.0, verticalLayoutEmptyLeft);
        verticalLayoutEmptyLeft.setWidth(null);
        verticalLayoutMiddle.addClassName(Padding.LARGE);
        horizontalLayoutMain.setFlexGrow(1.0, verticalLayoutMiddle);
        verticalLayoutMiddle.setWidth(null);
        h3.setText("Informace o účtu");
        verticalLayoutMiddle.setAlignSelf(FlexComponent.Alignment.START, h3);
        horizontalLayoutMiddle.setWidthFull();
        horizontalLayoutMiddle.addClassName(Gap.LARGE);
        horizontalLayoutMiddle.setFlexGrow(1.0, verticalLayoutInMiddle);
        verticalLayoutInMiddle.setWidth(null);
        passwordField.setLabel("Heslo");
        verticalLayoutInMiddle.setAlignSelf(FlexComponent.Alignment.CENTER, passwordField);
        textFieldUsername.setLabel("Uživatelské jméno");
        verticalLayoutInMiddle.setAlignSelf(FlexComponent.Alignment.CENTER, textFieldUsername);
        emailField.setLabel("Email");
        verticalLayoutInMiddle.setAlignSelf(FlexComponent.Alignment.CENTER, emailField);
        textFieldJobPosition.setLabel("Pozice");
        verticalLayoutInMiddle.setAlignSelf(FlexComponent.Alignment.CENTER, textFieldJobPosition);
        comboBoxOffice.setLabel("Stanice");
        verticalLayoutInMiddle.setAlignSelf(FlexComponent.Alignment.CENTER, comboBoxOffice);
        setComboBoxRoleData();
        setComboBoxOfficeData();
        comboBoxRole.setLabel("Role");
        verticalLayoutInMiddle.setAlignSelf(FlexComponent.Alignment.CENTER, comboBoxRole);
        horizontalLayoutButtons.addClassName(Gap.MEDIUM);
        horizontalLayoutButtons.setWidthFull();
        horizontalLayoutButtons.setAlignItems(Alignment.END);
        horizontalLayoutButtons.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonSave.setText("Uložit");
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonChangePassword.setText("Změnit heslo");
        buttonChangePassword.addClickListener(event -> openDialog());
        horizontalLayoutMain.setFlexGrow(1.0, verticalLayoutEmptyRight);
        verticalLayoutEmptyRight.setWidth(null);
        getContent().add(horizontalLayoutMain);
        horizontalLayoutMain.add(verticalLayoutEmptyLeft);
        horizontalLayoutMain.add(verticalLayoutMiddle);
        verticalLayoutMiddle.add(h3);
        verticalLayoutMiddle.add(horizontalLayoutMiddle);
        horizontalLayoutMiddle.add(verticalLayoutInMiddle);
        verticalLayoutInMiddle.add(textFieldUsername);
        verticalLayoutInMiddle.add(passwordField);
        verticalLayoutInMiddle.add(emailField);
        verticalLayoutInMiddle.add(textFieldJobPosition);
        verticalLayoutInMiddle.add(comboBoxOffice);
        verticalLayoutInMiddle.add(comboBoxRole);
        verticalLayoutMiddle.add(horizontalLayoutButtons);
        horizontalLayoutButtons.add(buttonSave);
        horizontalLayoutButtons.add(buttonChangePassword);
        horizontalLayoutMain.add(verticalLayoutEmptyRight);
        oldPassword = new PasswordField("Staré heslo");
        newPassword = new PasswordField("Nové heslo");
        binder = new Binder<>(UserInDto.class);
        binder.forField(textFieldUsername).withValidator(name -> name.length() > 4, "Username must have at least 5 characters").bind(UserInDto::getUsername, UserInDto::setUsername);
        binder.forField(textFieldJobPosition).withValidator(name -> name.length() > 1, "Job position must have at least 2 characters").bind(UserInDto::getJobPosition, UserInDto::setJobPosition);
        binder.forField(emailField).withValidator(new EmailValidator("Insert email.")).bind(UserInDto::getEmail, UserInDto::setEmail);

        Binder<NewPasswordDto> binderPassword = new Binder<>(NewPasswordDto.class);
        binderPassword.forField(oldPassword).withValidator(password -> password.length() > 7, "Password must have at least 8 characters").bind(NewPasswordDto::getOldPassword, NewPasswordDto::setOldPassword);
        binderPassword.forField(newPassword).withValidator(password -> password.length() > 7, "Password must have at least 8 characters").bind(NewPasswordDto::getNewPassword, NewPasswordDto::setNewPassword);
    }

    private void fillFields(UserOutDto userOutDto) {
        textFieldUsername.setValue(userOutDto.getUsername());
        emailField.setValue(userOutDto.getEmail());
        textFieldJobPosition.setValue(userOutDto.getJobPosition());
        comboBoxOffice.setValue(userOutDto.getBranchOfficeDto());
        comboBoxRole.setValue(userOutDto.getRole());
        this.userOutDto = userOutDto;
    }


    private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Změna hesla");

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(new H2("Změna hesla"));
        oldPassword.setValue("");
        newPassword.setValue("");
        HorizontalLayout horizontalLayoutButtons = new HorizontalLayout();
        Button buttonSave = new Button("Uložit");
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button buttonCancel = new Button("Zrušit");
        buttonSave.addClickListener(event -> {
            boolean isValid = !FieldValidator.validateEmptyField(oldPassword)
                    & !FieldValidator.validateEmptyField(newPassword);
            if (isValid) {
                NewPasswordDto newPasswordDto = new NewPasswordDto();
                newPasswordDto.setOldPassword(oldPassword.getValue());
                newPasswordDto.setNewPassword(newPassword.getValue());
                try {
                    userService.changePassword(userOutDto.getId(), newPasswordDto);
                    Notification.show("Heslo změněno", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    dialog.close();
                } catch (IllegalArgumentException e) {
                    Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }

        });
        buttonCancel.addClickListener(event -> dialog.close());
        horizontalLayoutButtons.add(buttonSave, buttonCancel);
        dialogLayout.add(oldPassword, newPassword, horizontalLayoutButtons);
        dialog.add(dialogLayout);
        dialog.open();
    }

    Binder<UserInDto> binder;

    private void editUser() {
        boolean isValid = !FieldValidator.validateEmptyField(textFieldUsername)
                & !FieldValidator.validateEmptyField(textFieldJobPosition)
                & !FieldValidator.validateEmptyField(comboBoxOffice)
                & !FieldValidator.validateEmptyField(comboBoxRole)
                & !FieldValidator.validateEmptyField(emailField);
        if (isValid && binder.isValid()) {
            UserInDto userInDto = new UserInDto();
            userInDto.setUsername(textFieldUsername.getValue());
            userInDto.setEmail(emailField.getValue());
            userInDto.setJobPosition(textFieldJobPosition.getValue());
            userInDto.setRole(comboBoxRole.getValue().getId());
            userInDto.setOffice(comboBoxOffice.getValue().getId());

            try {
                userService.editUser(userOutDto.getId(), userInDto);
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage(), 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            Notification.show("Uživatel upraven", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate(UserList.class);
        } else {
            Notification.show("Nesprávně vyplněna pole.", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);

        }
    }

    private void addUser() {
        boolean isValid = !FieldValidator.validateEmptyField(textFieldUsername)
                & !FieldValidator.validateEmptyField(textFieldJobPosition)
                & !FieldValidator.validateEmptyField(comboBoxOffice)
                & !FieldValidator.validateEmptyField(comboBoxRole)
                & !FieldValidator.validateEmptyField(emailField);
        if (isValid && binder.isValid()) {
            UserInDto userInDto = new UserInDto();
            userInDto.setUsername(textFieldUsername.getValue());
            userInDto.setEmail(emailField.getValue());
            userInDto.setJobPosition(textFieldJobPosition.getValue());
            userInDto.setRole(comboBoxRole.getValue().getId());
            userInDto.setOffice(comboBoxOffice.getValue().getId());
            userInDto.setPassword(passwordField.getValue());
            try {
                userService.addUser(userInDto);
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage(), 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            Notification.show("Uživatel přidán", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate(UserList.class);
        } else {
            Notification.show("Nesprávně vyplněna pole.", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void setComboBoxRoleData() {
        List<Role> allRoles = roleService.getAllRoles();

        comboBoxRole.setItems(allRoles);
        comboBoxRole.setItemLabelGenerator(Role::getDescription);
        if (userOutDto != null) {
            comboBoxRole.setValue(userOutDto.getRole());
        }
    }

    private void setComboBoxOfficeData() {
        List<BranchOfficeDto> allOffices = branchOfficeService.getAllOffices();
        comboBoxOffice.setItems(allOffices);
        comboBoxOffice.setItemLabelGenerator(BranchOfficeDto::getCity);
        if (userOutDto != null) {
            comboBoxOffice.setValue(userOutDto.getBranchOfficeDto());
        }
    }
}