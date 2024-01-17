export default class urlTools {

    static getUrlAllParams(url){
        const queryParamsString = url.split('?')[1];
        const params = {};
        if(queryParamsString) {
            queryParamsString.split('&').forEach(param => {
                const keyValue = param.split('=');
                const key = decodeURIComponent(keyValue[0]);
                const value = decodeURIComponent(keyValue[1]);
                params[key] = value;
            });
        }
        return params;
    }

}