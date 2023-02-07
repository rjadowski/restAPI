//JavaScript plugin derived from: https://www.jqueryscript.net/table/jQuery-JSON-To-Table-Plugin.html
(function ($) {

    $.fn.createTable = function (data, options) {

        var element = this;
        var settings = $.extend({}, $.fn.createTable.defaults, options);
        var selector;

        if (element[0].className !== undefined) {
            var split = element[0].className.split(' ');
            selector = '.' + split.join('.') + ' ';
        } else if (element[0].id !== undefined) {
            selector = '#' + element[0].id + ' ';
        }

        var table = '<table class="json-to-table">';

        table += '<thead id=\'tablehead\'>' +
            '<tr>' +
            '<th>ID</th>' +
            '<th>TITLE</th>' +
            '<th>YEAR</th>' +
            '<th>DIRECTOR</th>' +
            '<th>STARS</th>' +
            '<th>REVIEW</th>' +
            '</tr></thead>';
        table += '<tbody>';
        table += $.fn.createTable.parseTableData(data, false);
        table += '</tbody>';
        table += '</table>';

        element.html(table);

        return function () {

            $(selector + '.json-to-table').css({
                borderCollapse: 'collapse',
                width: '100%',
                border: settings.borderWidth + ' ' + settings.borderStyle + ' ' + settings.borderColor,
                fontFamily: settings.fontFamily
            });

            $(selector + '.jsl').css({
                minWidth: '18px',
                width: '18px',
                padding: '0 10px 0 10px'
            });

            $(selector + '.json-to-table thead th:not(:first-child), .json-to-table tbody td:not(:first-child)').css({
                width: (100 / $.fn.createTable.getHighestColumnCount(data).max) + '%'
            });

            $(selector + '.json-to-table thead th, .json-to-table tbody td').css({
                border: settings.borderWidth + ' ' + settings.borderStyle + ' ' + settings.borderColor
            });

            $(selector + '.json-to-table thead th').css({
                backgroundColor: settings.thBg,
                color: settings.thColor,
                height: settings.thHeight,
                fontFamily: settings.thFontFamily,
                fontSize: settings.thFontSize,
                textTransform: settings.thTextTransform
            });

            $(selector + '.json-to-table tbody td').css({
                color: settings.trColor,
                paddingLeft: settings.tdPaddingLeft,
                paddingRight: settings.tdPaddingRight,
                height: settings.trHeight,
                fontSize: settings.trFontSize,
                fontFamily: settings.trFontFamily
            });

            $(selector + '.json-to-table tbody tr:nth-child(odd)').css({
                backgroundColor: settings.trBgOdd,
            });

            $(selector + '.json-to-table tbody tr:nth-child(even)').css({
                backgroundColor: settings.trBgEven,
            });

        }();
    };

    $.fn.createTable.getHighestColumnCount = function (data) {

        var count = 0, temp = 0, column = {max: 0, when: 0};

        for (var i = 0; i < data.length; i++) {
            count = $.fn.getObjectLength(data[i]);
            if (temp <= count) {
                temp = count;
                column.max = count;
                column.when = i;
            }
        }

        return column;
    };

    $.fn.createTable.parseTableData = function (data, thead) {

        var row = '';

        for (var i = 0; i < data.length; i++) {
            $.each(data[i], function (key, value) {
                if (thead === true) {
                    if (i === $.fn.createTable.getHighestColumnCount(data).when) {
                        row += '<th>' + $.fn.humanize(key) + '</th>';
                    }
                } else if (thead === false) {
                    row += '<td>' + value + '</td>';
                }
            });
            if (thead === false) row += '</tr>';
        }

        return row;
    };

    $.fn.getObjectLength = function (object) {

        var length = 0;

        for (var key in object) {
            if (object.hasOwnProperty(key)) {
                ++length;
            }
        }

        return length;
    };

    $.fn.humanize = function (text) {

        var string = text.split('_');

        for (i = 0; i < string.length; i++) {
            string[i] = string[i].toUpperCase();
        }

        return string.join(' ');
    };

    $.fn.createTable.defaults = {
        borderWidth: '1px',
        borderStyle: 'solid',
        borderColor: '#DDDDDD',
        fontFamily: 'Verdana, Helvetica, Arial, FreeSans, sans-serif',

        thBg: '#F0FFFF',
        thColor: '#0E0E0E',
        thHeight: '30px',
        thFontFamily: 'Verdana, Helvetica, Arial, FreeSans, sans-serif',
        thFontSize: '14px',
        thTextTransform: 'capitalize',

        trBgOdd: '#F0FFFF',
        trBgEven: '#F5F5DC',
        trColor: '#0E0E0E',
        trHeight: '25px',
        trFontFamily: 'Verdana, Helvetica, Arial, FreeSans, sans-serif',
        trFontSize: '13px',

        tdPaddingLeft: '10px',
        tdPaddingRight: '10px'
    }

}(jQuery));