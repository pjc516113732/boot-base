package com.smile.boot.autoconfigure.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger")
public class SpringFoxProperties {

    /** 是否启用 swagger 配置 */
    private boolean enabled = true;

    /** 接口样例数据字段是否根据类定义字段的顺序排列 */
    private boolean classOrdered = true;

    private String applicationPath = "/";

    private String documentationPath = "/api-docs";

    private String title = "";

    private String description = "通用API描述";

    private String termsOfServiceUrl;

    private String license;

    private String licenseUrl;

    private String version = "1.0.0";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isClassOrdered() {
        return classOrdered;
    }

    public void setClassOrdered(boolean classOrdered) {
        this.classOrdered = classOrdered;
    }

    public String getApplicationPath() {
        return applicationPath;
    }

    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    public String getDocumentationPath() {
        return documentationPath;
    }

    public void setDocumentationPath(String documentationPath) {
        this.documentationPath = documentationPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 联系信息，比如作者
     *
     */
    private ContactInfo contact = new ContactInfo();

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    public static class ContactInfo{
        private String name = "";
        private String url;
        private String email = "";

        public ContactInfo() {
        }

        public ContactInfo name(String name) {
            this.setName(name);
            return this;
        }

        public ContactInfo url(String url) {
            this.setUrl(url);
            return this;
        }

        public ContactInfo email(String email) {
            this.setEmail(email);
            return this;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
