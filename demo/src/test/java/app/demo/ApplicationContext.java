package app.demo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

public class ApplicationContext implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        // public key MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNGtFhM481UGVHClaSWnldqC8Lksqqf5KK6fS2cTxL9m4VvvKzHGM4X8pTbTr3QkgE6Vxo+VVoLu5KAJp4bFdk/Fqb7m6FIMkb1N2XKXVT3MmPShFmC59UyoCEA5qBbeW5NQNTqPn7qqsFX7TQiA/WgQwrVtKTRg4CQQxwv3nSbQIDAQAB
        System.setProperty("private.key", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM0a0WEzjzVQZUcKVpJaeV2oLwuSyqp/korp9LZxPEv2bhW+8rMcYzhfylNtOvdCSATpXGj5VWgu7koAmnhsV2T8WpvuboUgyRvU3ZcpdVPcyY9KEWYLn1TKgIQDmoFt5bk1A1Oo+fuqqwVftNCID9aBDCtW0pNGDgJBDHC/edJtAgMBAAECgYAzAzSIZHZ94B0QajXV+MKu1KG+0yes+EXOXxoKNme0zPncVr2uZqUW5FxCG6zKyqV280OUqE7htqp1pWjGAsxJUzRPAQcfhdtAVC/c+f9LwDiiYqxotdwyQNYMdWO1Ayquf02cbV3mHXLa3fyYfheMKskO80n4XSw+liZfq7l+AQJBAPsYoZnG4VH/cjHYeD3/TALTuu+rCEn61ALaC4CaVarkyWPU7dcljW8BgchWX3Px3OFT8/6PiatG/ghPpuKef+ECQQDRHD/6uRiEDHiU4cvnDynYgmJnnIdPGotBRONBWVcwgeJno3CD9Orx9OVEM262QqCxqt7ixSXDKZaPOneIZ9QNAkEAwY6IK7kiQYovJftfHTNqDZfXNx7ZYeXHfndEfJr7xKNeFuWxfbKF5vb7a0ohhIgBo3SK1kUhc3VjJ1b3JyPVIQJBAKWkpSGswbjIUDRPcL/FiTZSgjTlD2If+rNnKEW5yZFJMdk65IQC3KFxhrMxsr95o+VZH1mvJaylIviuLcXFyXkCQG8tBmnbm0HrEUi8jUTlOOXhVTfZwh7kZ4uYFluJ8e68MITYGRYeiyVU8RQvW01tevZvmikmzARGwREl7DbeQHk=");
        System.setProperty("jwt.expiration.minutes", "1000");
    }

}