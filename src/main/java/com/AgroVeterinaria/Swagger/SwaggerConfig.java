package com.AgroVeterinaria.Swagger;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.AgroVeterinaria.Herramientas.Constantes;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {//apiDocket
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(
					 	RequestHandlerSelectors.basePackage("com.TuPaginaWeb.Registro.Usuarios.UserController")
					 )
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiCatalogos())
				;
	}
	
	public ApiInfo getApiCatalogos() {
		return new ApiInfo(
				"Tu Pagina Web Hoy",
				"Tu Pagina Web Hoy Description",
				"1.0",
				Constantes.URL_BASE,//"http://TuPaginaWebHoy.com/terms",
				new Contact("TuPaginaWebHoy", "http://TuPaginaWebHoy.com", "ferbethasistemas@gmail.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);
	}
}