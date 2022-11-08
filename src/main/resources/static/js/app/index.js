function Dataframe(_tuples) {
    this.tuples = _tuples;
    this.csvData = Dataframe.prototype.tuplesToCSV(_tuples);
}

Dataframe.prototype.saveCSV = function (_filename) {
    let filename = _filename
    if (window.navigator && window.navigator.msSaveOrOpenBlob)
    {
        let blob = new Blob([decodeURIComponent(this.stringData)], {
            type: 'text/csv;charset=utf8'
        });
        window.navigator.msSaveOrOpenBlob(blob, filename);
    } else if (window.Blob && window.URL) {
        // HTML5 Blob
        let blob = new Blob([this.stringData], { type: 'text/csv;charset=utf8' });
        let csvUrl = URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.setAttribute('style', 'display:none');
        a.setAttribute('href', csvUrl);
        a.setAttribute('download', filename);
        document.body.appendChild(a);

        a.click()
        a.remove();
    } else {
        // Data URI
        let csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(this.stringData);
        let blob = new Blob([this.stringData], { type: 'text/csv;charset=utf8' });
        let csvUrl = URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.setAttribute('style', 'display:none');
        a.setAttribute('target', '_blank');
        a.setAttribute('href', csvData);
        a.setAttribute('download', filename);
        document.body.appendChild(a);

        a.click()
        a.remove();
    }
}

Dataframe.prototype.tuplesToCSV = function(_tuples)
{
    const keys = Object.keys(_tuples[0]);
    let CSVString = "\uFEFF"; //BOM

    for(let i = 0; i < keys.length ; i++)
    {
        CSVString += keys[i] + ",";
    }
    CSVString += "\r\n"

    _tuples.forEach((v) => {
        for(let i = 0; i < keys.length ; i++)
        {
            let value = v[keys[i]];
            if(value.toString().includes(","))
                CSVString += "\"" + value + "\"" + ",";
            else
                CSVString += value + ",";
        }
        CSVString += "\r\n"
    })

    return CSVString;
}

Dataframe.prototype.showTable = function()
{
    this.removeTable();
    let tbparent = document.getElementById('result');
    let allRows = this.tuples;
    let table = '<table id = "resultTable">';
    for (let singleRow = 0; singleRow < allRows.length; singleRow++) {
        if (singleRow === 0) {
            table += '<thead>';
            table += '<tr>';
        } else {
            table += '<tr>';
        }

        let row = allRows[singleRow];
        let rowKeys = Object.keys(allRows[singleRow]);
        for(let i = 0; i < rowKeys.length; i++){
            if(singleRow === 0){
                table += '<th>';
                table += rowKeys[i];
                table += '</th>';
            } else {
                table += '<td>';
                table += row[rowKeys[i]];
                table += '</td>';
            }
        }
        if (singleRow === 0) {
            table += '</tr>';
            table += '</thead>';
            table += '<tbody>';
        } else {
            table += '</tr>';
        }
    }
    table += '</tbody>';
    table += '</table>';

    tbparent.insertAdjacentHTML("afterbegin", table);
}

Dataframe.prototype.removeTable = function()
{
    let tbparent = document.getElementById('result');
    let tb = document.getElementById('resultTable');
    if(tb != null)
        tbparent.removeChild(tb);
}

var dataframe;

function getCheckboxValue()  {
    var value_str = document.getElementById('searchRange');
    console.log(value_str.options[value_str.selectedIndex].value);
    // 선택된 목록 가져오기
    const query = 'input[name="search"]:checked';
    const selectedEls =
        document.querySelectorAll(query);
    const rangeValue = document.getElementById('searchRange').value
    const inputValue = document.querySelector('input[name="customInputField"]').value

    // 선택된 목록에서 value 찾기
    let result = [];
    selectedEls.forEach((el) => {
        if(el.value != "name"){
            result.push(el.value);
        }
        else{
            result.push("fname");
            result.push("lname");
            result.push("minit");
        }
    });

    postAjax(result, rangeValue, inputValue);
}

function postAjax(_attribuite, _selectRange, _search)
{
    // Type은 List<String>, String, String 입니다.
    let testObj = {attribute : _attribuite, selectRange : _selectRange, search : _search}

    console.log(testObj);
    $.ajax({
        type: 'POST',
        url:'/employee',
        data: JSON.stringify(testObj),
        dataType:'json',
        contentType:'application/json; charset=utf-8'
    }).done(function(rs) {
        alert('POST 성공');
        console.log(rs);
        dataframe = new Dataframe((replaceNullorZero(rs)));
        dataframe.showTable();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function replaceNullorZero(_objs)
{
    console.log(_objs);
    const keys = Object.keys(_objs[0]);
    _objs.forEach((v) => {
        for(let i = 0; i < keys.length ; i++)
        {
            if(v[keys[i]] == null || v[keys[i]] == 0)
                v[keys[i]] = ""
        }
    })

    return _objs;
}