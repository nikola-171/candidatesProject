package project.recruitment.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.lang.model.SourceVersion;
import javax.tools.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Set;

@RequestMapping("/users")
public interface AppUserController
{

    @GetMapping("")
     String test();
}
