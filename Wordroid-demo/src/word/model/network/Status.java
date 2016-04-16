package word.model.network;

public class Status {

	private int error_code;
	private String reason;
	private Result result;
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}


	/*  "result": {
        "data": {
            "translation": [
                "apple"
            ],
            "basic": {
                "phonetic": "píng guǒ",
                "explains": [
                    "[园艺] apple"
                ]
            },
            "query": "苹果",
            "web": [
                {
                    "value": [
                        "Apple",
                        "iphone",
                        "IPOD"
                    ],
                    "key": "苹果"
                },
                {
                    "value": [
                        "Apple Inc",
                        "AAPL",
                        "Apple Computer"
                    ],
                    "key": "苹果公司"
                },
                {
                    "value": [
                        "Apple Media Player",
                        "DoiceApple"
                    ],
                    "key": "苹果新锐"
                }
            ]
        }*/
	
}
