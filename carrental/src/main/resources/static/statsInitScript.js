var initStats = function (days) {
    $.get('/admin/stats/rentRequests/' + days, function (data, status) {
        const labels = data.labels;
        const elements = data.data;
        var ctx = document.getElementById('rentRequests');
        var firstChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Prośby o wypożyczenia w ostatnim tygodniu',
                    data: elements,
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    });

    $.get('/admin/stats/rents/' + days, function (data, status) {
        const labels = data.labels;
        const elements = data.data;
        var ctx = document.getElementById('rents');
        var secondChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Wypożyczenia w ostatnim tygodniu',
                    data: elements,
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    });
};