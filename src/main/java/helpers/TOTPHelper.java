package helpers;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.TOTPGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class TOTPHelper {

    private static final Logger LOG = LoggerFactory.getLogger(TOTPHelper.class);

    public static String getTOTP(String key) {
        TOTPGenerator totpGenerator = new TOTPGenerator.Builder(key.getBytes()).withHOTPGenerator(builder -> {
            builder.withPasswordLength(6);
            builder.withAlgorithm(HMACAlgorithm.SHA1); // SHA256 and SHA512 are also supported
        }).withPeriod(Duration.ofSeconds(30)).build();
        String totp = totpGenerator.now();
        LOG.info("Generated TOTP {} successfully", totp);
        boolean isValid = totpGenerator.verify(totp);
        if (!isValid) { // handle edge cases when the TOTP is about to expire
            LOG.error("The generated TOTP is invalid, attempting to generate the TOTP again");
            return getTOTP(key);
        } else {
            LOG.info("The generated TOTP is valid");
        }
        return totp;
    }
}
