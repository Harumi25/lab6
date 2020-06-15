package models;

public class Register {

    String first_name;
    String last_name;
    String gender;
    String dob;
    String Email;
    String Phone;

    String Status;

    public Register( String first_name, String Last_Name,  String Gender, String dob, String Email, String Phone, String Status) {
        //this.ID= ID;
        this.first_name = first_name;
        this.last_name = Last_Name;
        this.gender = Gender;
        this.dob = dob;
        this.Email = Email;
        this.Phone = Phone;
        this.Status = Status;
    }

    public String getfirst_name() {
        return first_name;
    }

    public void setfirst_name(String first_Name) {
        this.first_name = first_Name;
    }

    public String getlast_name() {
        return last_name;
    }

    public void setlast_name(String last_Name) {
        this.last_name = last_Name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
