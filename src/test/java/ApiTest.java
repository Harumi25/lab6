import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import models.Register;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class ApiTest {
    String token = "2paen0qk-o8c6GXDRJKRWCWY-itq9gsXRqOm";
    @BeforeClass
    public void beforeClass() {
        RestAssured.baseURI = "https://gorest.co.in/";
    }
    @Test
    public void poz_test1_1() {
        given().auth().oauth2(token)
                .when().get("public-api/users")
                .then().log().all()
                .statusCode(200);

    }

    @DataProvider(name = "name")
    public Object[][] name()
    {
        return  new Object[][]{
                {"Adrianna"}
        };
    }
    @Test(dataProvider = "name")
    public void poz_test1_2(String first_name)
    {
        System.out.println("received list of users with the specified name: ");
        given().auth().oauth2(token)
                .when().get("public-api/users?first_name=" + first_name)
                .then().log().all()
                .assertThat()
                .body("result.first_name", equalTo(first_name))
                .statusCode(200);
    }

    @DataProvider(name = "create_new_user")
    public Object[][] create_new_user()
    {
        return new Object[][] {{"Ekaterina", "Tozik", "2000-01-18","female", "eeee@gmail.com", "+8 (996) 189-74-35", "active"}};
    }

    @Test(dataProvider = "create_new_user")
    public void poz_test1_3(String first_name, String last_name, String dob, String gender, String email, String phone, String Status)
    {
        Register human = new Register(first_name, last_name,  gender,dob, email, phone, Status);
        System.out.println("created a new user");
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(human)
                .when().post("public-api/users")
                .then().log().all()
                .body("result.first_name", equalTo(first_name))
                .body("result.last_name", equalTo(last_name))
                .body("result.gender", equalTo(gender))
                .body("result.email", equalTo(email))
                .body("result.dob", equalTo(dob))
                .body("result.phone", equalTo(phone))
                .body("result.status", equalTo(Status))
                .statusCode(302);
    }

    @DataProvider(name = "get_user_id")
    public Object[][] get_user_id()
    {
        return  new Object[][]{
                {1805}
        };
    }
    @Test(dataProvider = "get_user_id")
    public void poz_test1_4(int id)
    {
        System.out.println("Received user with selected id:");
        given().auth().oauth2(token)
                .when().get("public-api/users/"+ id)
                .then()
                .log().all()
                .body("result.id", equalTo(Integer.toString(id)))
                .statusCode(200);
    }

    @DataProvider(name = "change_user__ID")
    public Object[][] change_user__ID()
    {
        return new Object[][] {{1802, "Ola", "Kris",  "male", "2000-07-07", "123@gmail.com", "+8 (902) 111-77-44" , "active"}};
    }
    @Test(dataProvider = "change_user__ID")

    public void test1_5(int id,String first_name, String last_name, String gender, String dob, String email, String phone, String Status )
    {
        Register post =  new Register( first_name, last_name,gender, dob, email, phone, Status);
        System.out.println("change_user_with_specified_ID:");
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(post)
                .when().put("public-api/users/" + id)
                .then()
                .log().all().body("result.id", equalTo(Integer.toString(id)))
                .body("result.first_name", equalTo(first_name))
                .body("result.last_name", equalTo(last_name))
                .body("result.gender", equalTo(gender))
                .body("result.email", equalTo(email))
                .body("result.dob", equalTo(dob))
                .body("result.phone", equalTo(phone))
                .body("result.status", equalTo(Status))
                .statusCode(200);
    }


    @DataProvider(name = "delete_user_ID")
    public Object[][] delete_user_ID()
    {
        return new Object[][] {{1809}};
    };
    @Test(dataProvider = "delete_user_ID")
    public void poz_test1_6(int id) {
        System.out.println("delete user with specified id");
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .when().delete("public-api/users/" + id)
                .then().log().all()
                .body("result", equalTo(null))

                .statusCode(200);
    }

    @DataProvider(name = "Val_token")
    public Object[][] Val_token()
    {
        return new Object[][] {{""}, {"sdfsdf"}};
    };
    @Test(dataProvider = "Val_token")
    public void neg_test2_1(String tok) {
        System.out.println("Without specifying an authorization token and with an incorrect token:");
        given().auth().oauth2(tok)
                .when().get("public-api/users")
                .then().log().all()
                .body("result.status", equalTo(401))
                .body("result.message", equalTo("Your request was made with invalid credentials."))

                .statusCode(200);
    }

    @DataProvider(name = "incorrect_body")
    public Object[][] incorrect_body()
    {
        return new Object[][] {{"Ekaterina", "Tozik", "2000-01-18","female", "eeee@gmail.com", "+8 (996) 189-74-35", "active"}};
    }

    @Test(dataProvider = "incorrect_body")
    public void neg_test2_2(String first_name, String last_name, String dob, String gender, String email, String phone, String Status)
    {
        Register post1 = new Register(first_name, last_name,  gender,dob, email, phone, Status);
        System.out.println("With incorrect request body format:");
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(post1)
                .when().post("public-api/users")
                .then().log().all()
                .body("_meta.code", equalTo(422))
                .body("result.message", equalTo("Email 1234@gmail.com has already been taken."))
                .statusCode(200);
    }

    @DataProvider(name = "delete")
    public Object [][] delete()
    {
        return new Object[][] {{324}};// рандомное число
    }
    @Test(dataProvider = "delete")
    public void neg_test2_3(int id)
    {
        System.out.println("Delete:");
        given().auth().oauth2(token)
                .contentType(ContentType.JSON)
                .when().delete("public-api/users/" + id)
                .then().log().all()
                .body("_meta.code", equalTo(404))

                .statusCode(200);
    }
}


