package it.smallcode.smallpets.core.version;
/*

Class created by SmallCode
19.07.2021 20:16

*/

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

public class Version {

    @Getter
    private int mainVersion = 1, subVersion = 0, revisionVersion = 0 ;

    @Getter
    private String type;

    public Version(int mainVersion, int subVersion, int revisionVersion, String type) {
        this.mainVersion = mainVersion;
        this.subVersion = subVersion;
        this.revisionVersion = revisionVersion;
        this.type = type;
    }

    public Version(String versionString) {
        try {
            String[] split = versionString.split("-");
            if (split.length == 2) {
                this.type = split[1];
            }
            String version = split[0];
            String[] versions = version.split(Pattern.quote("."));
            this.mainVersion = Integer.parseInt(versions[0]);
            this.subVersion = Integer.parseInt(versions[1]);
            this.revisionVersion = Integer.parseInt(versions[2]);
        }catch (Exception ex){
            throw new IllegalArgumentException("Illegal version string");
        }
    }

    public boolean bigger(Version version){
        if(getMainVersion() < version.getMainVersion()){
            return false;
        }else if(getMainVersion() > version.getMainVersion())
            return true;

        if(getSubVersion() < version.getSubVersion()){
            return false;
        }else if(getSubVersion() > version.getSubVersion())
            return true;

        if(getRevisionVersion() < version.getRevisionVersion() || version.getRevisionVersion() == getRevisionVersion()){
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return mainVersion == version.mainVersion && subVersion == version.subVersion && revisionVersion == version.revisionVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainVersion, subVersion, revisionVersion, type);
    }

    public String toString(){
        String ret = mainVersion + "." + subVersion + "." + revisionVersion;
        if(type != null)
            ret += "-" + type;
        return ret;
    }

}
