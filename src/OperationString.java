public class OperationString {
    /**
     * 去除字符串的左右空格
     * @return
     */
    public static String Trim(String test) {
        int left=0;
        int right=test.length()-1;
        for(int i=0; i<test.length(); i++) {
            if(test.charAt(i)!=' ') {
                left=i;
                break;
            }
        }
        for(int i=test.length()-1; i>0; i--) {
            if(test.charAt(i)!=' ') {
                right=i;
                break;
            }
        }
        if(left>right) {
            return "";
        }else if(left==right){
            if(test.charAt(left)!=' ') {
                return test.substring(left,left+1);
            }else{
                return "";
            }
        }else{
            return test.substring(left,right+1);
        }
    }
}
