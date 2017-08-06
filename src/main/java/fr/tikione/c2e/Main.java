package fr.tikione.c2e;

import com.google.common.base.Strings;
import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.tikione.c2e.model.web.Auth;
import fr.tikione.c2e.model.web.Magazine;
import fr.tikione.c2e.service.GlobalModule;
import fr.tikione.c2e.service.html.HtmlWriterService;
import fr.tikione.c2e.service.web.CPCAuthService;
import fr.tikione.c2e.service.web.scrap.CPCReaderService;
import fr.tikione.gui.MainApp;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Slf4j
public class Main {
    
    public static boolean DEBUG = false;
    public static String VERSION = "1.2.3";
    
    // params: username password [-gui] [-debug] [-list] [-cpc360 -cpc361...|-cpcall] [-html] [-nopic] [-compresspic]
    public static void main(String... args) throws Exception {
        log.info("TikiOne C2E version {}, Java {}", VERSION, System.getProperty("java.version"));
        assert args != null;
        
        String[] argsToShow = args.clone();
        if (argsToShow.length > 0) {
            argsToShow[1] = Strings.repeat("*", argsToShow[1].length());
        }
        log.info("les paramètres de lancement sont : {}", Arrays.toString(argsToShow));
        
        List<String> argsList = Arrays.asList(args);
        DEBUG = argsList.contains("-debug");
        if (argsList.contains("-gui")) {
            startGUI(args);
        } else {
            startCLI(args);
        }
    }
    
    private static void startCLI(String... args)
            throws IOException, InterruptedException {
        assert args.length > 2;
        List<String> switchList = Arrays.asList(args).subList(2, args.length);
        boolean list = switchList.contains("-list");
        boolean includePictures = !switchList.contains("-nopic");
        boolean compressPictures = switchList.contains("-compresspic");
        boolean doHtml = switchList.contains("-html");
        boolean allMags = switchList.contains("-cpcall");
        
        Injector cpcInjector = Guice.createInjector(new GlobalModule());
        CPCAuthService cpcAuthService = cpcInjector.getInstance(CPCAuthService.class);
        CPCReaderService cpcReaderService = cpcInjector.getInstance(CPCReaderService.class);
        
        Auth auth = cpcAuthService.authenticate(args[0], args[1]);
        List<Integer> headers = cpcReaderService.listDownloadableMagazines(auth);
        List<Integer> magNumbers = new ArrayList<>();
        if (allMags) {
            magNumbers.addAll(headers);
        } else {
            for (String arg : args) {
                if (arg.startsWith("-cpc")) {
                    try {
                        magNumbers.add(Integer.parseInt(arg.substring("-cpc".length())));
                    } catch (NumberFormatException nfe) {
                        log.debug("un numéro du magazine CPC est mal tapé, il sera ignoré");
                    }
                }
            }
        }
        if (list) {
            log.info("les numéros disponibles sont : {}", headers);
        }
        
        if (doHtml) {
            if (magNumbers.size() > 1) {
                log.info("téléchargement des numéros : {}", magNumbers);
            }
            for (int i = 0; i < magNumbers.size(); i++) {
                int magNumber = magNumbers.get(i);
                Magazine magazine = cpcReaderService.downloadMagazine(auth, magNumber);
                File file = new File("CPC" + magNumber
                        + (includePictures ? "" : "-nopic")
                        + (compressPictures ? "-compresspic" : "")
                        + ".html");
                HtmlWriterService writerService = cpcInjector.getInstance(HtmlWriterService.class);
                writerService.write(magazine, file, includePictures, compressPictures);
                if (i != magNumbers.size() - 1) {
                    log.info("pause de 30s avant de télécharger le prochain numéro");
                    for (int j = 0; j < 30; j++) {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    log.info(" ok\n");
                }
            }
        }
        
        log.info("terminé !");
    }
    
    private static void startGUI(String... args)
            throws Exception {
        Application.launch(MainApp.class, args);
    }
}
