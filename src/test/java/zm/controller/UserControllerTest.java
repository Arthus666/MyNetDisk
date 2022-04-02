package zm.controller;

import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class UserControllerTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String word = null;
        int  id=2;
        MessageDigest digest = MessageDigest.getInstance("md5");
        digest.update(("FuckYou!"+id).getBytes());
        word = new BigInteger(1, digest.digest()).toString(16);
        System.out.println(word);

        String s = DigestUtils.md5DigestAsHex(("FuckYou!" + id).getBytes());
        System.out.println(s);
    }

}
