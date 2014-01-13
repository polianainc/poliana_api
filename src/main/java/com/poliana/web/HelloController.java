package com.poliana.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello World! From the Poliana team
 *
 * @author David Gilmore
 * @date 12/15/13
 */
@Controller
@RequestMapping("/")
public class HelloController extends AbstractBaseController {

    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index() {
        return "Hello. Welcome to the Poliana API! The API is structures as follows:" +
                "   Global Parameters: " +
                "       ?format=json/csv\n" +
                "\n" +
                "       ?api_key=XXX\n" +
                "       \n" +
                "       ?fields\n" +
                "\n" +
                "       ?start=1010101&end=12309314 \n" +
                "       ?year=2003\n" +
                "       ?congress=110  DEFAULT current\n" +
                "       \n" +
                "       ?compare_to=analysis_type\n" +
                "\n" +
                "       ?plot=type\n" +
                "\n" +
                "/politician \n" +
                "\n" +
                "       /contributions\n" +
                "              /organizations\n" +
                "                     ?politician_id=XXXXXX *\n" +
                "                     ?organization_id=XXXXX\n" +
                "                     \n" +
                "              /industries\n" +
                "                     ?politician_id=XXXXXX *\n" +
                "                     ?industry_id=XXX\n" +
                "                     ?category_id=XXXXX\n" +
                "                     \n" +
                "              /pacs\n" +
                "                     ?politician_id=XXXXXX *\n" +
                "                     ?pac_id=XXXX\n" +
                "       \n" +
                "       /expenditures\n" +
                "\n" +
                "       /committees\n" +
                "\n" +
                "       /votes\n" +
                "\n" +
                "       /sponsorships\n" +
                "\n" +
                "/industry\n" +
                "\n" +
                "       /contributions\n" +
                "              ?industry_id=XXX **\n" +
                "              ?category_id=XXXXX **\n" +
                "              ?chamber=s\n" +
                "\n" +
                "\n" +
                "       /memberships\n" +
                "\n" +
                "\n" +
                "/pac\n" +
                "\n" +
                "       /contributions \n" +
                "\n" +
                "              /pacs\n" +
                "                     ?pac_id=XXX *\n" +
                "                     ?other_pac_id=XXX\n" +
                "                     ?chamber=s\n" +
                "\n" +
                "              /politician\n" +
                "                     ?pac_id *\n" +
                "                     ?politician_id\n" +
                "                     ?chamber=s\n" +
                "\n" +
                "\n" +
                "/bill\n" +
                "\n" +
                "       /amendments\n" +
                "       /votes\n" +
                "       /related\n" +
                "\n" +
                "/congress";
    }

}