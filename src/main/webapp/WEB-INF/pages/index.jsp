<!DOCTYPE HTML>
<html>
<head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <script type="text/javascript">
        google.charts.load('current', {'packages':['bar']});
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Year', 'Sales', 'Expenses', 'Profit'],
                ['2014', ${pa1}, ${pa2}, ${pa3}],
                ['2015', ${pb1}, ${pb2}, ${pb3}],
                ['2016', ${pc1}, ${pc2}, ${pc3}],
                ['2017', ${pd1}, ${pd2}, ${pd3}],
            ]);

            var options = {
                chart: {
                    title: 'Company Performance',
                    subtitle: 'Sales, Expenses, and Profit: 2014-2017',
                }
            };

            var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

            chart.draw(data, options);
        }
    </script>
</head>
<body>
<a href="test">Go to test</a>

<div id="columnchart_material" style="width: 900px; height: 500px;"></div>
</body>
</html>