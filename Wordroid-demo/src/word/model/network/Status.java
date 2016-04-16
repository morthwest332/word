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
                "phonetic": "p��ng gu��",
                "explains": [
                    "[԰��] apple"
                ]
            },
            "query": "ƻ��",
            "web": [
                {
                    "value": [
                        "Apple",
                        "iphone",
                        "IPOD"
                    ],
                    "key": "ƻ��"
                },
                {
                    "value": [
                        "Apple Inc",
                        "AAPL",
                        "Apple Computer"
                    ],
                    "key": "ƻ����˾"
                },
                {
                    "value": [
                        "Apple Media Player",
                        "DoiceApple"
                    ],
                    "key": "ƻ������"
                }
            ]
        }*/
	
}