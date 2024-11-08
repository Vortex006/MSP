<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>检验报告单</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0 auto;
            padding: 20px;
            box-sizing: border-box;
        }

        .report-container {
            max-width: 900px;
            margin: auto;
            background-color: #f4f4f4;
            padding: 20px;
            border-radius: 5px;
        }

        .header {
            text-align: center;
            margin-bottom: 20px;
        }

        .header h1 {
            margin: 0;
            color: #333;
        }

        .patient-info {
            display: flex;
            justify-content: left;
            margin-bottom: 20px;
        }

        .patient-info th {
            text-align: left;
        }

        .patient-info p {
            margin: 0;
        }

        .results-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .results-table th,
        .results-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        .results-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        .footer {
            margin-top: 20px;
            width: 80%;
        }

        .footer th {
            text-align: center;
        }

        .footer td {
            text-align: left;
        }

        .footer p {
            margin: 0;
        }

        .end {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="report-container">
    <div class="header">
        <h1>检验报告单</h1>
    </div>
    <div class="patient-info">
        <table style="width: 100%">
            <tr>
                <th>检验仪器</th>
                <td>${instrument}</td>
                <th>检验目的</th>
                <td>${itemName}</td>
                <th>科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;室</th>
                <td>${deptment}</td>
            </tr>
            <tr>
                <th>标本编号</th>
                <td>${itemCode}</td>
                <th>标本类型</th>
                <td>${itemType}</td>
                <th>条码编号</th>
                <td>${itemNo}</td>
            </tr>
            <tr>
                <th>门急诊号</th>
                <td>${patientId}</td>
                <th>临床诊断</th>
                <td>${itemResult}</td>
                <th>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</th>
                <td>${patientName}</td>
            </tr>
            <tr>
                <th>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</th>
                <td>${patientSex}</td>
                <th>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</th>
                <td>${patientAge}岁</td>
                <th>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</th>
                <td>${remark}</td>
            </tr>
        </table>
    </div>
    <table class="results-table">
        <thead>
        <tr>
            <th>代号</th>
            <th>名称</th>
            <th>结果</th>
            <th>参考范围</th>
            <th>单位</th>
        </tr>
        </thead>
        <tbody>
        <#list labList as item>
            <tr>
                <td>${item.itemCode}</td>
                <td>${item.itemName}</td>
                <td>${item.itemResult}</td>
                <td>${item.itemRR}</td>
                <td>${item.itemUnit}</td>
            </tr>
        </#list>
        </tbody>
    </table>

    <div class="end">
        <div class="footer">
            <table style="width: 100%">
                <tr>
                    <th>送检医师</th>
                    <td>${itemDoctor}</td>
                    <th>检&nbsp;&nbsp;验&nbsp;&nbsp;者</th>
                    <td>${labDoctor}</td>
                </tr>
                <tr>
                    <th>复&nbsp;&nbsp;核&nbsp;&nbsp;者</th>
                    <td>${checkDoctor}</td>
                    <th>采集时间</th>
                    <td>${itemTime}</td>
                </tr>
                <tr>
                    <th>检验时间</th>
                    <td>${labTime}</td>
                    <th>报告时间</th>
                    <td>${checkTime}</td>
                </tr>
            </table>
            <br/>
            <p>声明：本报告仅对本标本负责，如有疑问，请在三日内联系0377-61609078。项目名称前有*标志的，即为全国认可项目（全国版）。</p>
        </div>
        <img src="D:\seal\seal.png" alt="电子印章"/>
    </div>
</div>
</body>
</html>
