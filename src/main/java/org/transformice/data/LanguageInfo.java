package org.transformice.data;

public enum LanguageInfo {
    AF("af", "Afrikaans", "za"),
    AZ("az", "Azərbaycan dili", "az"),
    ID("id", "Bahasa Indonesia", "id"),
    MS("ms", "Bahasa Melayu", "my"),
    BI("bi", "Bislama", "vu"),
    BS("bs", "Bosanski jezik", "ba"),
    CA("ca", "Català", "ad"),
    NY("ny", "ChiCheŵa", "mw"),
    DA("da", "Dansk", "dk"),
    DE("de", "Deutsch", "de"),
    ET("et", "Eesti keel", "ee"),
    NA("na", "Ekakairũ Naoero", "nr"),
    EN("en", "English", "gb"),
    ES("es", "Español", "es"),
    TO("to", "Faka Tonga", "to"),
    MG("mg", "Fiteny malagasy", "mg"),
    FR("fr", "Français", "fr"),
    SM("sm", "Gagana fa'a Samoa", "ws"),
    HR("hr", "Hrvatski", "hr"),
    IT("it", "Italiano", "it"),
    MH("mh", "Kajin M̧ajeļ", "mh"),
    KL("kl", "Kalaallisut", "gl"),
    RN("rn", "KiRundi", "bi"),
    RW("rw", "Kinyarwanda", "rw"),
    SW("sw", "Kiswahili", "ke"),
    HT("ht", "Kreyòl ayisyen", "ht"),
    LV("lv", "Latviešu valoda", "lv", false, true),
    LT("lt", "Lietuvių kalba", "lt", false, true),
    LB("lb", "Lëtzebuergesch", "lu"),
    HU("hu", "Magyar", "hu"),
    MT("mt", "Malti", "mt"),
    NL("nl", "Nederlands", "nl"),
    NO("no", "Norsk", "no"),
    UZ("uz", "O'zbek", "uz"),
    PL("pl", "Polski", "pl"),
    PT("pt", "Português", "pt"),
    BR("br", "Português brasileiro", "br"),
    RO("ro", "Română", "ro", false, true),
    QU("qu", "Runa Simi", "bo"),
    ST("st", "SeSotho", "ls"),
    TN("tn", "SeTswana", "bw"),
    SQ("sq", "Shqip", "al"),
    SS("ss", "SiSwati", "sz"),
    SK("sk", "Slovenčina", "sk", false, true),
    SL("sl", "Slovenščina", "si", false, true),
    SO("so", "Soomaaliga", "so"),
    FI("fi", "Suomen kieli", "fi"),
    SV("sv", "Svenska", "se"),
    TL("tl", "Tagalog", "ph"),
    VI("vi", "Tiếng Việt", "vn"),
    TK("tk", "Türkmen", "tm", false, true),
    TR("tr", "Türkçe", "tr", false, true),
    FJ("fj", "Vosa Vakaviti", "fj"),
    WO("wo", "Wollof", "sn"),
    YO("yo", "Yorùbá", "ng"),
    IS("is", "Íslenska", "is"),
    CS("cs", "Česky", "cz", false, true),
    EL("el", "Ελληνικά", "gr", false, true),
    BE("be", "Беларуская", "by", false, true),
    KY("ky", "Кыргыз тили", "kg", false, true),
    MK("mk", "Македонски јазик", "mk", false, true),
    MN("mn", "Монгол", "mn", false, true),
    RU("ru", "Русский язык", "ru", false, true),
    SR("sr", "Српски језик", "rs", false, true),
    TG("tg", "Тоҷикӣ", "tj", false, true),
    UK("uk", "Українська мова", "ua", false, true),
    BG("bg", "български език", "bg", false, true),
    KK("kk", "Қазақ тілі", "kz", false, true),
    HY("hy", "Հայերեն", "am", false, true),
    HE("he", "עברית", "il", true, true),
    UR("ur", "اردو", "pk", true, true, "Tahoma"),
    AR("ar", "العربية", "iar", true, true, "Tahoma"),
    FA("fa", "فارسی", "ir", true, true, "Tahoma"),
    DV("dv", "ދިވެހި", "mv", true, true, "Tahoma"),
    NE("ne", "नेपाली", "np", false, true, "Tahoma"),
    HI("hi", "हिन्दी", "in", false, true, "Tahoma"),
    BN("bn", "বাংলা", "bd", false, true, "Tahoma"),
    TA("ta", "தமிழ்", "lk", false, true, "Tahoma"),
    TH("th", "ไทย", "th", false, true),
    LO("lo", "ພາສາລາວ", "la", false, true, "Tahoma"),
    DZ("dz", "རྫོང་ཁ", "bt", false, true, "Tahoma"),
    MY("my", "ဗမာစာ", "mm", false, true, "Tahoma"),
    KA("ka", "ქართული", "ge", false, true, "Tahoma"),
    TI("ti", "ትግርኛ", "er", false, true, "Tahoma"),
    AM("am", "አማርኛ", "et", false, true, "Tahoma"),
    KM("km", "ភាសាខ្មែរ", "kh", false, true, "Tahoma"),
    CN("cn", "中国简体", "cn", false, true, "Tahoma"),
    ZH("zh", "中國繁體", "hk", false, true, "Tahoma"),
    JA("ja", "日本語", "jp", false, true, "Tahoma"),
    KO("ko", "한국어", "kr", false, true, "Tahoma");

    private final String code;
    private final String name;
    private final String countryCode;
    private final boolean rightToLeft;
    private final boolean specialLayout;
    private final String font;

    LanguageInfo(String code, String name, String countryCode) {
        this(code, name, countryCode, false, false, "");
    }

    LanguageInfo(String code, String name, String countryCode, boolean rightToLeft, boolean specialLayout) {
        this(code, name, countryCode, rightToLeft, specialLayout, "");
    }

    LanguageInfo(String code, String name, String countryCode, boolean rightToLeft, boolean specialLayout, String font) {
        this.code = code;
        this.name = name;
        this.countryCode = countryCode;
        this.rightToLeft = rightToLeft;
        this.specialLayout = specialLayout;
        this.font = font;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public boolean isRightToLeft() {
        return this.rightToLeft;
    }

    public boolean hasSpecialLayout() {
        return this.specialLayout;
    }

    public String getFont() {
        return this.font;
    }

    public static LanguageInfo fromCode(String code) {
        for (LanguageInfo language : values()) {
            if (language.code.equalsIgnoreCase(code)) {
                return language;
            }
        }

        return EN;
    }
}