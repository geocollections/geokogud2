package ee.ttu.geocollection.domain;

import java.util.Arrays;
import java.util.List;

/**
 * LookUpTypes which start with 'i' character mean it is case insensitive
 */
public enum LookUpType {
    exact,
    iexact,
    in,         // Is included in list
    range,
    contains,
    icontains,
    startswith,
    istartswith,
    endswith,
    iendswith,
    gt,         // Greater than, '>'
    lt,         // Less than '<'
    gte,        // Greater than or equal to '>='
    lte,        // Less than or equal to '<='
    isnull,
    hierarchy,
    //API-s non defined values
    doesnotcontain,
    doesnotexact,
    ;

    /**
     * Changes 'doesnot' to '!', API has this feature,
     * but Application is not using it, but it is still available.
     * @return Enum's name
     */
    public String value() {
        if(this.equals(LookUpType.doesnotcontain)) return "icontains!";
        else if(this.equals(LookUpType.doesnotexact)) return "iexact!";
        return name();
    }

    // TODO: is it used?, probably not
    public static LookUpType fromValue(String v) {
        return valueOf(v);
    }

    // TODO: is it used?, probably not
    public static List<LookUpType> getTextFieldLookUpTypes() {
        return Arrays.asList(contains,exact,startswith,endswith,icontains,in);
    }
}