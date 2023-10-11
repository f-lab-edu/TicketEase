package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Map;
import java.util.Random;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class Simulation1 extends Simulation {

	private HttpProtocolBuilder httpProtocol = http
		.baseUrl("http://115.85.183.44:8080")
		.inferHtmlResources(AllowList(),
			DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2",
				".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
		.acceptHeader(
			"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,zh-CN;q=0.6,zh;q=0.5")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader(
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");

	private Map<CharSequence, String> headers_0 = Map.ofEntries(
		Map.entry("Cache-Control", "max-age=0"),
		Map.entry("Proxy-Connection", "keep-alive")
	);

	private Map<CharSequence, String> headers_1 = Map.of("Proxy-Connection", "keep-alive");

	private Map<CharSequence, String> headers_3 = Map.ofEntries(
		Map.entry("Cache-Control", "max-age=0"),
		Map.entry("Origin", "http://115.85.183.44:8080"),
		Map.entry("Proxy-Connection", "keep-alive")
	);

	private Random random = new Random(100);
	private String username = "hello" + random.nextInt(1000);
	private String password = "1234qwer" + random.nextInt(1000);

	private ScenarioBuilder scn = scenario("Simulation1")
		.exec(
			http("메인페이지 접근")
				.get("/")
				.headers(headers_0)
		)
		.exec(
			http("로그인페이지 접근")
				.get("/login")
				.headers(headers_1)
		)
		.exec(
			http("회원가입페이지 접근")
				.get("/signup")
				.check(
					regex("<input type=\"hidden\" name=\"_csrf\" value=\"([^\\\"]+)\"")
						.saveAs("csrfToken")
				)
				.headers(headers_1)

		)
		.exec(
			http("회원가입 수행")
				.post("/signup")
				.headers(headers_3)
				.formParam("_csrf", "${csrfToken}")
				.formParam("nickname", username)
				.formParam("password", password)
				.formParam("confirmPassword", password)
		)
		.exec(
			http("로그인 수행")
				.post("/login")
				.headers(headers_3)
				.formParam("_csrf", "${csrfToken}")
				.formParam("nickname", username)
				.formParam("password", password)
		)
		.exec(
			http("상세페이지")
				.get("/detail/70")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지")
				.get("/")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지")
				.get("/detail/69")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지")
				.get("/")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지 검색")
				.get("/?search=%EB%89%B4%EC%A7%84%EC%8A%A4")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지")
				.get("/detail/57")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지")
				.get("/?search=%EB%89%B4%EC%A7%84%EC%8A%A4")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지")
				.get("/detail/56")
				.headers(headers_1)
		)
		.exec(
			http("상세페이지")
				.get("/?search=%EB%89%B4%EC%A7%84%EC%8A%A4")
				.headers(headers_1)
		);

	{
		setUp(scn.injectOpen(atOnceUsers(100))).protocols(httpProtocol);
	}
}
