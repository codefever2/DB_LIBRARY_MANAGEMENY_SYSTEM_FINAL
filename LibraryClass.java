import java.util.regex.Pattern;

public abstract class LibraryClass
{
          protected abstract void startOption();
          protected abstract void getUserOrAdminDetailsAtEntry();
          protected abstract void viewRegisteredId();
          protected abstract void resetPassword();
          protected boolean passwordValidation(String password)
          {
             boolean update= Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",password);
              return  update;
          }
}
