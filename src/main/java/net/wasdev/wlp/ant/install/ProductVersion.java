/**
 * (C) Copyright IBM Corporation 2018.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.wasdev.wlp.ant.install;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductVersion implements Comparable<ProductVersion> {

    private final int major;
    private final int minor;
    private final int micro;
    private final int qualifier;

    private ProductVersion(int major, int minor, int micro, int qualifier) {
        this.major = major;
        this.minor = minor;
        this.micro = micro;
        this.qualifier = qualifier;
    }

    public static ProductVersion parseVersion(String version) {
        Pattern p = Pattern.compile("^(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?(?:\\.(\\d+))?$");
        Matcher m = p.matcher(version);

        if (m.find()) {
            int major = parseComponent(m.group(1));
            int minor = parseComponent(m.group(2));
            int micro = parseComponent(m.group(3));
            int qualifier = parseComponent(m.group(4));
            return new ProductVersion(major, minor, micro, qualifier);
        } else {
            throw new IllegalArgumentException("Invalid version: " + version);
        }
    }

    private static int parseComponent(String version) {
        if (version == null) {
            return 0;
        } else {
            return Integer.parseInt(version);
        }
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ProductVersion)) {
            return false;
        }
        return compareTo((ProductVersion)other) == 0;
    }

    public int compareTo(ProductVersion other) {
        if (other == this) {
            return 0;
        }

        int result = major - other.major;
        if (result != 0) {
            return result;
        }
        result = minor - other.minor;
        if (result != 0) {
            return result;
        }
        result = micro - other.micro;
        if (result != 0) {
            return result;
        }
        result = qualifier - other.qualifier;
        if (result != 0) {
            return result;
        }
        return 0;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getMicro() {
        return micro;
    }

    public int getQualifier() {
        return qualifier;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(major);
        result.append('.');
        result.append(minor);
        result.append('.');
        result.append(micro);
        result.append('.');        
        result.append(qualifier);
        return result.toString();
    }
}
