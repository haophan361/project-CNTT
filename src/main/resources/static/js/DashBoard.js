//Biểu đồ tròn doanh thu cho từng loại sản phẩm
document.addEventListener("DOMContentLoaded", function()
{
    fetch('/admin/revenue_typeProduct')
        .then(response => response.json())
        .then(data =>
        {
            const labels = data.map(item => item.type);
            const series = data.map(item => item.revenue.toFixed(0));

            const colors = [
                '#FF5733', '#33FF57', '#3357FF', '#F0FF33', '#FF33A1',
                '#8B33FF', '#FF8B33', '#33FFE0', '#FF33F6', '#33FF8B'
            ];
            const chartData =
            {
                labels: labels,
                datasets:
                [{
                    data: series,
                    backgroundColor: colors.slice(0, series.length),
                    hoverBackgroundColor: colors.slice(0, series.length)
                }]
            };

            const chartOptions =
            {
                responsive: true,
                plugins:
                {
                    legend:
                    {
                        position: 'top',
                    },
                    tooltip:
                    {
                        callbacks:
                        {
                            label: function(tooltipItem)
                            {
                                return `${tooltipItem.label}: ${tooltipItem.raw}%`;
                            }
                        }
                    }
                }
            };
            const ctx = document.getElementById('chartPreferences').getContext('2d');
            new Chart(ctx, {
                type: 'pie',
                data: chartData,
                options: chartOptions
            });
        })
        .catch(error => console.error('Error loading data:', error));
});


//Biểu đồ đường doanh thu theo tháng
document.addEventListener("DOMContentLoaded", function ()
{
    const ctx = document.getElementById('chartRevenue_Time').getContext('2d');
    let lineChart;

    document.getElementById('yearSelect').addEventListener('change', function ()
    {
        const selectedYear = this.value;

        fetch(`/admin/revenue_Month?year=${selectedYear}`)
            .then(response => response.json())
            .then(data =>
            {
                const months = data.map(item => `${item.month}`);
                const revenues = data.map(item => (item.revenue / 1000000).toFixed(2));

                if (lineChart)
                {
                    lineChart.destroy();
                }

                lineChart = new Chart(ctx,
                    {
                        type: 'line',
                        data:
                            {
                                labels: months,
                                datasets:
                                    [{
                                        label: `Doanh thu năm ${selectedYear}`,
                                        data: revenues,
                                        borderColor: '#00008B',
                                        pointBackgroundColor: '#00008B',
                                        fill: true,
                                        tension: 0.1
                                    }]
                            },
                        options:
                            {
                                responsive: true,
                                plugins:
                                    {
                                        legend:
                                            {
                                                display: true,
                                                position: 'top'
                                            },
                                        tooltip:
                                            {
                                                callbacks:
                                                    {
                                                        label: function(tooltipItem)
                                                        {
                                                            return `Tháng ${tooltipItem.label}: ${tooltipItem.raw} triệu VNĐ`;
                                                        }
                                                    }
                                            }
                                    },
                                scales:
                                    {
                                        x:
                                            {
                                                title:
                                                    {
                                                        display: true,
                                                        text: 'Tháng'
                                                    }
                                            },
                                        y:
                                            {
                                                title:
                                                    {
                                                        display: true,
                                                        text: 'Doanh thu (triệu VNĐ)'
                                                    },
                                                beginAtZero: true
                                            }
                                    }
                            }
                    });
            })
            .catch(error => console.error('Error fetching data:', error));
    });
    document.getElementById('yearSelect').dispatchEvent(new Event('change'));
});


//Biểu đồ cột danh sách sản phẩm bán chạy nhất
document.addEventListener("DOMContentLoaded", function ()
{
    const ctx = document.getElementById('topSellerChart').getContext('2d');
    let barChart;

    document.getElementById('numberProduct').addEventListener('change', function ()
    {
        const selectedNumber = this.value;

        fetch(`/admin/revenue_Product?numberProduct=${selectedNumber}`)
            .then(response => response.json())
            .then(data =>
            {
                const productNames = data.map(item => item.product_name);
                const revenues = data.map(item => (item.revenue_product/1000000).toFixed(2));

                if (barChart)
                {
                    barChart.destroy();
                }

                barChart = new Chart(ctx,
                {
                    type: 'bar',
                    data:
                    {
                        labels: productNames,
                        datasets:
                        [{
                            label: 'Doanh thu (triệu VNĐ)',
                            data: revenues,
                            backgroundColor: '#3e95cd',
                            borderColor: '#00008B',
                            borderWidth: 1
                        }]
                    },
                    options:
                    {
                        responsive: true,
                        plugins:
                        {
                            legend:
                            {
                                display: true,
                                position: 'top'
                            },
                            tooltip:
                            {
                                callbacks:
                                {
                                    label: function(tooltipItem)
                                    {
                                        return `${tooltipItem.label}: ${tooltipItem.raw} triệu VNĐ`;
                                    }
                                }
                            }
                        },
                        scales:
                        {
                            x:
                            {
                                title:
                                {
                                    display: true,
                                    text: 'Sản phẩm'
                                }
                            },
                            y:
                            {
                                title:
                                {
                                    display: true,
                                    text: 'Doanh thu (triệu VNĐ)'
                                },
                                beginAtZero: true
                            }
                        }
                    }
                });
            })
            .catch(error => console.error('Error fetching data:', error));
    });
    document.getElementById('numberProduct').dispatchEvent(new Event('change'));
});
