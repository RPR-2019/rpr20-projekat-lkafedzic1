package ba.unsa.etf.rpr.project;

import java.util.Date;

public interface Person {

    public int getId();

    public void setId(int id);

    public String getFirstname();

    public void setFirstname(String firstname);

    public String getLastname();

    public void setLastname(String lastname);

    public Gender getGender();

    public void setGender(Gender gender);

    public String getDateOfBirth();

    public void setDateOfBirth(String dateOfBirth);

}
