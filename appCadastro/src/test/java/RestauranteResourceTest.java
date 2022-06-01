import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.approvaltests.Approvals;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;


@DBRider
//@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {

    @Test
    @DataSet("restaurante-senario1.yml")
    public void testBuscarRestaurantes() {
        String resultado = given()
                .when().get("/restaurantes")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().asString();
        Approvals.verifyJson(resultado);
    }
}
