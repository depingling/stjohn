function encodeSingleAndDoubleQuotes(stringToEncode) {
    if (stringToEncode == null) {
        return stringToEncode;
    }
    var encodedString = stringToEncode;
    encodedString = encodedString.replace('"', "&quot;");
    encodedString = encodedString.replace("'", "&#39;");    
    return encodedString;
}

function trimStringLeft(stringToTrim) {
    if (stringToTrim == null) {
        return stringToTrim;
    }
    if (stringToTrim.length == 0) {
        return stringToTrim;
    }
    var trimmedString = stringToTrim;
    var lenString = trimmedString.length;
    var pos = -1;
    for (var i = 0; i < lenString; i++) {
        if (trimmedString.charAt(i) == ' ') {
            pos += 1;
        }
        else {
            break;
        }
    }
    if (pos >= 0) {
        trimmedString = trimmedString.substring(pos + 1, lenString - pos - 1);
    }
    return trimmedString;
}

function trimStringRight(stringToTrim) {
    if (stringToTrim == null) {
        return stringToTrim;
    }
    if (stringToTrim.length == 0) {
        return stringToTrim;
    }
    var trimmedString = stringToTrim;
    var lenString = trimmedString.length;
    var pos = lenString;
    for (var i = lenString - 1; i >= 0; i--) {
        if (trimmedString.charAt(i) == ' ') {
            pos -= 1;
        }
        else {
            break;
        }
    }
    if (pos < lenString) {
        trimmedString = trimmedString.substring(0, pos);
    }
    return trimmedString;
}

function trimString(stringToTrim) {
    var trimmedString = stringToTrim;
    trimmedString = trimStringLeft(trimmedString);
    trimmedString = trimStringRight(trimmedString);
    return trimmedString;
}

function compareStringsIgnoreBlankSpaces(string1, string2) {
    if (string1 == null && string2 == null) {
        return 0;
    }
    if (string1 == null && string2 != null) {
        return -1;
    }
    if (string1 != null && string2 == null) {
        return 1;
    }
    var comparedString1 = trimString(string1);
    var comparedString2 = trimString(string2);    
    var lenString1 = comparedString1.length;
    var lenString2 = comparedString2.length;
    if (lenString1 != lenString2) {
        if (lenString1 > lenString2) {
            return 1;
        }
        else {
            return -1;
        }
    }
    for (var i = 0; i < lenString1; i++) {
        if (comparedString1.charAt(i) != comparedString2.charAt(i)) {
            if (comparedString1.charAt(i) > comparedString2.charAt(i)) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }
    return 0;
}
