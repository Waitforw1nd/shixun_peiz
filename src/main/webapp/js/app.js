// 使用一个对象来管理所有图表实例
const charts = {
    districtChart: null,
    communityChart: null,
    layoutChart: null,
    areaSegmentChart: null,
    decorationChart: null,
    orientationChart: null,
    decadeChart: null,
    correlationChart: null,
    districtAreaChart: null
};

// 初始化所有图表
function initCharts() {
    if (typeof echarts === 'undefined') {
        console.error('ECharts 未定义，无法初始化图表');
        return;
    }

    // 初始化图表实例
    charts.districtChart = echarts.init(document.getElementById('districtChart'));
    charts.communityChart = echarts.init(document.getElementById('communityChart'));
    charts.layoutChart = echarts.init(document.getElementById('layoutChart'));
    charts.areaSegmentChart = echarts.init(document.getElementById('areaSegmentChart'));
    charts.decorationChart = echarts.init(document.getElementById('decorationChart'));
    charts.orientationChart = echarts.init(document.getElementById('orientationChart'));
    charts.decadeChart = echarts.init(document.getElementById('decadeChart'));
    charts.correlationChart = echarts.init(document.getElementById('correlationChart'));
    charts.districtAreaChart = echarts.init(document.getElementById('districtAreaChart'));

    // 设置初始加载状态
    const loadingOption = {
        title: {
            text: '数据加载中...',
            left: 'center',
            top: 'center',
            textStyle: {
                color: '#a4b0be',
                fontSize: 18,
                fontWeight: 'normal'
            }
        }
    };

    // 为所有图表设置加载状态
    Object.values(charts).forEach(chart => {
        if (chart) chart.setOption(loadingOption);
    });
}

// 更新日期时间显示
function updateDateTime() {
    const now = new Date();
    const dateStr = now.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        weekday: 'long'
    });

    const timeStr = now.toLocaleTimeString('zh-CN', {
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });

    document.getElementById('current-date').textContent = dateStr;
    document.getElementById('current-time').textContent = timeStr;
}

// 核心修复：确保数据是数组类型
function ensureDataIsArray(data) {
    if (!data) return [];
    if (Array.isArray(data)) return data;

    // 处理可能的对象响应
    if (typeof data === 'object') {
        // 尝试提取可能的数组属性
        const keys = Object.keys(data);
        for (const key of keys) {
            if (Array.isArray(data[key])) {
                return data[key];
            }
        }
    }

    console.warn('数据不是数组格式', data);
    return [];
}

// 从Servlet获取数据并更新图表
async function fetchDataAndUpdateCharts() {
    try {
        // 显示加载状态
        showLoadingState();

        // 重置统计数据
        document.getElementById('total-listings').textContent = '0';
        document.getElementById('avg-unit-price').textContent = '¥0万';
        document.getElementById('avg-area').textContent = '0㎡';
        document.getElementById('last-update').textContent = '最后更新: 加载中...';
        document.querySelector('#system-info .status-ok').textContent = "数据加载中";

        // 获取统计数据
        const statsResponse = await fetch('/second-hand-data-analysis/districtMarketStats');
        if (!statsResponse.ok) throw new Error('统计数据请求失败');
        const statsData = await statsResponse.json();
        updateStatsBar(ensureDataIsArray(statsData));

        // 定义所有数据端点
        const endpoints = [
            { name: 'districtChart', url: '/second-hand-data-analysis/districtMarketStats' },
            { name: 'communityChart', url: '/second-hand-data-analysis/communityRanking' },
            { name: 'layoutChart', url: '/second-hand-data-analysis/layoutAnalysis' },
            { name: 'areaSegmentChart', url: '/second-hand-data-analysis/areaSegmentAnalysis' },
            { name: 'decorationChart', url: '/second-hand-data-analysis/decorationAnalysis' },
            { name: 'orientationChart', url: '/second-hand-data-analysis/orientationAnalysis' },
            { name: 'decadeChart', url: '/second-hand-data-analysis/decadePriceTrend' },
            { name: 'correlationChart', url: '/second-hand-data-analysis/priceAreaCorrelation' },
            { name: 'districtAreaChart', url: '/second-hand-data-analysis/districtAreaAnalysis' }
        ];

        // 逐个获取数据而不是并行，避免部分失败导致整体崩溃
        for (const endpoint of endpoints) {
            try {
                const response = await fetch(endpoint.url);
                if (!response.ok) {
                    throw new Error(`${endpoint.url} 请求失败: ${response.status}`);
                }

                const data = await response.json();
                const cleanData = ensureDataIsArray(data);

                // 更新对应图表
                switch(endpoint.name) {
                    case 'districtChart':
                        updateDistrictChart(cleanData);
                        break;
                    case 'communityChart':
                        updateCommunityChart(cleanData);
                        break;
                    case 'layoutChart':
                        updateLayoutChart(cleanData);
                        break;
                    case 'areaSegmentChart':
                        updateAreaSegmentChart(cleanData);
                        break;
                    case 'decorationChart':
                        updateDecorationChart(cleanData);
                        break;
                    case 'orientationChart':
                        updateOrientationChart(cleanData);
                        break;
                    case 'decadeChart':
                        updateDecadeChart(cleanData);
                        break;
                    case 'correlationChart':
                        updateCorrelationChart(cleanData);
                        break;
                    case 'districtAreaChart':
                        updateDistrictAreaChart(cleanData);
                        break;
                }
            } catch (error) {
                console.error(`${endpoint.name} 加载失败:`, error);
                showChartError(endpoint.name, error.message);
            }
        }

        // 更新最后刷新时间
        const now = new Date();
        document.getElementById('last-update').textContent =
            `最后更新: ${now.toLocaleTimeString('zh-CN')}`;

        // 更新系统状态
        document.querySelector('#system-info .status-ok').textContent = "运行正常";

    } catch (error) {
        console.error('数据加载失败:', error);
        showErrorState(error.message);
    }
}

// 显示加载状态
function showLoadingState() {
    Object.values(charts).forEach(chart => {
        if (chart) {
            chart.showLoading({
                text: '加载数据中...',
                color: '#4facfe',
                textColor: '#e0e7ff',
                maskColor: 'rgba(15, 25, 45, 0.7)'
            });
        }
    });
}

// 显示单个图表错误
function showChartError(chartName, message) {
    const chart = charts[chartName];
    if (chart) {
        chart.hideLoading();
        chart.setOption({
            title: {
                text: '数据加载失败',
                subtext: message || '请检查网络连接或服务器状态',
                left: 'center',
                top: 'center',
                textStyle: {
                    color: '#e74c3c',
                    fontSize: 18
                },
                subtextStyle: {
                    color: '#a4b0be',
                    fontSize: 14
                }
            }
        });
    }
}

// 显示全局错误状态
function showErrorState(message) {
    Object.values(charts).forEach(chart => {
        if (chart) {
            chart.hideLoading();
            chart.setOption({
                title: {
                    text: '数据加载失败',
                    subtext: message || '请检查网络连接或服务器状态',
                    left: 'center',
                    top: 'center',
                    textStyle: {
                        color: '#e74c3c',
                        fontSize: 18
                    },
                    subtextStyle: {
                        color: '#a4b0be',
                        fontSize: 14
                    }
                }
            });
        }
    });

    // 更新系统状态显示
    const systemInfo = document.getElementById('system-info');
    if (systemInfo) {
        systemInfo.innerHTML = `系统状态: <span style="color:#e74c3c;">数据加载失败 - ${message || '未知错误'}</span>`;
    }

    // 重置统计信息
    document.getElementById('total-listings').textContent = '0';
    document.getElementById('avg-unit-price').textContent = '¥0万';
    document.getElementById('avg-area').textContent = '0㎡';
}

// 更新统计信息栏
function updateStatsBar(data) {
    if (!Array.isArray(data) || data.length === 0) {
        document.getElementById('total-listings').textContent = '0';
        document.getElementById('avg-unit-price').textContent = '¥0万';
        document.getElementById('avg-area').textContent = '0㎡';
        return;
    }

    // 计算总数和平均值
    const totalListings = data.reduce((sum, item) => sum + (item.total_listings || 0), 0);
    const totalPrice = data.reduce((sum, item) => sum + (item.avg_total_price || 0) * (item.total_listings || 1), 0);
    const totalArea = data.reduce((sum, item) => sum + (item.avg_area || 0) * (item.total_listings || 1), 0);

    // 防止除以零错误
    const avgTotalPrice = totalListings > 0 ? totalPrice / totalListings : 0;
    const avgArea = totalListings > 0 ? totalArea / totalListings : 0;
    const avgUnitPrice = avgArea > 0 ? avgTotalPrice / avgArea : 0;

    // 更新DOM
    document.getElementById('total-listings').textContent =
        totalListings.toLocaleString();
    document.getElementById('avg-unit-price').textContent =
        '¥' + (avgUnitPrice / 10000).toFixed(1) + '万';
    document.getElementById('avg-area').textContent =
        avgArea.toFixed(1) + '㎡';
}

// ======================= 图表更新函数 =======================

// 更新区域市场统计图表
function updateDistrictChart(data) {
    if (!charts.districtChart || !Array.isArray(data) || data.length === 0) {
        showChartError('districtChart', '无有效数据');
        return;
    }

    const districts = data.map(item => item.district || '未知区域');
    const listings = data.map(item => item.total_listings || 0);
    const prices = data.map(item => item.avg_unit_price || 0);

    const option = {
        title: {
            text: '区域市场统计',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'shadow' },
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        legend: {
            data: ['房源数量', '平均单价(万)'],
            textStyle: { color: '#a4b0be' },
            bottom: 0
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '12%',
            top: '15%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: districts,
            axisLine: {
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: {
                rotate: 30,
                color: '#a4b0be'
            }
        },
        yAxis: [
            {
                type: 'value',
                name: '房源数量',
                nameTextStyle: { color: '#a4b0be' },
                axisLine: {
                    show: true,
                    lineStyle: { color: '#4facfe' }
                },
                axisLabel: { color: '#a4b0be' },
                splitLine: {
                    lineStyle: { color: 'rgba(100, 180, 255, 0.1)' }
                }
            },
            {
                type: 'value',
                name: '平均单价(万)',
                nameTextStyle: { color: '#a4b0be' },
                axisLine: {
                    show: true,
                    lineStyle: { color: '#00f2fe' }
                },
                axisLabel: { color: '#a4b0be' },
                splitLine: { show: false }
            }
        ],
        series: [
            {
                name: '房源数量',
                type: 'bar',
                data: listings,
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        { offset: 0, color: '#83bff6' },
                        { offset: 1, color: '#188df0' }
                    ])
                },
                barMaxWidth: 30
            },
            {
                name: '平均单价',
                type: 'line',
                yAxisIndex: 1,
                data: prices,
                symbol: 'circle',
                symbolSize: 8,
                itemStyle: {
                    color: '#00f2fe'
                },
                lineStyle: {
                    width: 3
                }
            }
        ]
    };

    charts.districtChart.setOption(option);
    charts.districtChart.hideLoading();
}

// 更新小区排名图表
function updateCommunityChart(data) {
    if (!charts.communityChart || !Array.isArray(data) || data.length === 0) {
        showChartError('communityChart', '无有效数据');
        return;
    }

    // 按房源数量排序
    const sortedData = [...data].sort((a, b) => (b.listing_count || 0) - (a.listing_count || 0));
    // 取前10名
    const top10 = sortedData.slice(0, 10);

    const option = {
        title: {
            text: '小区排名',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} 套房源',
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top: '10%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            axisLine: {
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: { color: '#a4b0be' },
            splitLine: {
                lineStyle: { color: 'rgba(100, 180, 255, 0.1)' }
            }
        },
        yAxis: {
            type: 'category',
            data: top10.map(item => item.community || '未知小区'),
            axisLine: {
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: { color: '#a4b0be' }
        },
        series: [
            {
                name: '房源数量',
                type: 'bar',
                data: top10.map(item => item.listing_count || 0),
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                        { offset: 0, color: '#83bff6' },
                        { offset: 1, color: '#188df0' }
                    ])
                },
                barMaxWidth: 20,
                label: {
                    show: true,
                    position: 'right',
                    color: '#e0e7ff'
                }
            }
        ]
    };

    charts.communityChart.setOption(option);
    charts.communityChart.hideLoading();
}

// 更新户型分析图表
function updateLayoutChart(data) {
    if (!charts.layoutChart || !Array.isArray(data) || data.length === 0) {
        showChartError('layoutChart', '无有效数据');
        return;
    }

    const option = {
        title: {
            text: '户型分析',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} ({d}%)',
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        legend: {
            orient: 'vertical',
            right: 10,
            top: 'center',
            textStyle: { color: '#a4b0be' }
        },
        series: [
            {
                name: '户型分布',
                type: 'pie',
                radius: ['40%', '70%'],
                center: ['40%', '50%'],
                data: data.map(item => ({
                    name: item.layout || '未知户型',
                    value: item.count || 0
                })),
                label: {
                    color: '#e0e7ff'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#0f1b3a',
                    borderWidth: 2
                }
            }
        ]
    };

    charts.layoutChart.setOption(option);
    charts.layoutChart.hideLoading();
}

// 更新面积段分布图表
function updateAreaSegmentChart(data) {
    if (!charts.areaSegmentChart || !Array.isArray(data) || data.length === 0) {
        showChartError('areaSegmentChart', '无有效数据');
        return;
    }

    // 按面积段排序
    const sortedData = [...data].sort((a, b) =>
        parseInt(a.area_segment || 0) - parseInt(b.area_segment || 0)
    );

    const option = {
        title: {
            text: '面积段分布',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'shadow' },
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top: '15%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: sortedData.map(item => `${item.area_segment || '0'}㎡`),
            axisLine: {
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: { color: '#a4b0be' }
        },
        yAxis: {
            type: 'value',
            name: '房源数量',
            nameTextStyle: { color: '#a4b0be' },
            axisLine: {
                show: true,
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: { color: '#a4b0be' },
            splitLine: {
                lineStyle: { color: 'rgba(100, 180, 255, 0.1)' }
            }
        },
        series: [
            {
                name: '房源数量',
                type: 'bar',
                data: sortedData.map(item => item.count || 0),
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        { offset: 0, color: '#4facfe' },
                        { offset: 1, color: '#00f2fe' }
                    ])
                },
                barMaxWidth: 40
            }
        ]
    };

    charts.areaSegmentChart.setOption(option);
    charts.areaSegmentChart.hideLoading();
}

// 更新装修情况图表
function updateDecorationChart(data) {
    if (!charts.decorationChart || !Array.isArray(data) || data.length === 0) {
        showChartError('decorationChart', '无有效数据');
        return;
    }

    const option = {
        title: {
            text: '装修情况',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} ({d}%)',
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        legend: {
            bottom: 10,
            textStyle: { color: '#a4b0be' }
        },
        series: [
            {
                name: '装修情况',
                type: 'pie',
                radius: '50%',
                center: ['50%', '45%'],
                data: data.map(item => ({
                    name: item.decoration || '未知装修',
                    value: item.count || 0
                })),
                label: {
                    color: '#e0e7ff'
                },
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                itemStyle: {
                    borderRadius: 5,
                    borderColor: '#0f1b3a',
                    borderWidth: 2
                }
            }
        ]
    };

    charts.decorationChart.setOption(option);
    charts.decorationChart.hideLoading();
}

// 更新房屋朝向图表
function updateOrientationChart(data) {
    if (!charts.orientationChart || !Array.isArray(data) || data.length === 0) {
        showChartError('orientationChart', '无有效数据');
        return;
    }

    // 将数据转换为雷达图所需格式
    const maxValue = Math.max(...data.map(d => d.count || 0), 1) * 1.2;
    const indicatorData = data.map(item => ({
        name: item.orientation || '未知朝向',
        max: maxValue
    }));

    const values = data.map(item => item.count || 0);

    const option = {
        title: {
            text: '房屋朝向',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}: {c}',
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        radar: {
            indicator: indicatorData,
            splitArea: {
                areaStyle: {
                    color: ['rgba(100, 180, 255, 0.1)', 'rgba(100, 180, 255, 0.05)']
                }
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(100, 180, 255, 0.3)'
                }
            },
            splitLine: {
                lineStyle: {
                    color: 'rgba(100, 180, 255, 0.3)'
                }
            },
            name: {
                textStyle: {
                    color: '#a4b0be'
                }
            }
        },
        series: [
            {
                name: '房屋朝向',
                type: 'radar',
                data: [
                    {
                        value: values,
                        name: '房源数量',
                        areaStyle: {
                            color: 'rgba(79, 172, 254, 0.3)'
                        },
                        lineStyle: {
                            width: 2,
                            color: '#4facfe'
                        },
                        symbol: 'circle',
                        symbolSize: 6,
                        label: {
                            show: true,
                            formatter: '{c}',
                            color: '#e0e7ff'
                        }
                    }
                ]
            }
        ]
    };

    charts.orientationChart.setOption(option);
    charts.orientationChart.hideLoading();
}

// 更新年代价格趋势图表
function updateDecadeChart(data) {
    if (!charts.decadeChart || !Array.isArray(data) || data.length === 0) {
        showChartError('decadeChart', '无有效数据');
        return;
    }

    // 按年代排序
    const sortedData = [...data].sort((a, b) =>
        parseInt(a.built_decade || 0) - parseInt(b.built_decade || 0)
    );

    const option = {
        title: {
            text: '年代价格趋势',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'line' },
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        legend: {
            data: ['平均单价(万)', '房源数量'],
            textStyle: { color: '#a4b0be' },
            bottom: 0
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '12%',
            top: '15%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: sortedData.map(item => `${item.built_decade || '未知'}年代`),
            axisLine: {
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: { color: '#a4b0be' }
        },
        yAxis: [
            {
                type: 'value',
                name: '平均单价(万)',
                nameTextStyle: { color: '#a4b0be' },
                axisLine: {
                    show: true,
                    lineStyle: { color: '#4facfe' }
                },
                axisLabel: { color: '#a4b0be' },
                splitLine: {
                    lineStyle: { color: 'rgba(100, 180, 255, 0.1)' }
                }
            },
            {
                type: 'value',
                name: '房源数量',
                nameTextStyle: { color: '#a4b0be' },
                axisLine: {
                    show: true,
                    lineStyle: { color: '#00f2fe' }
                },
                axisLabel: { color: '#a4b0be' },
                splitLine: { show: false }
            }
        ],
        series: [
            {
                name: '平均单价(万)',
                type: 'line',
                data: sortedData.map(item => item.avg_unit_price || 0),
                symbol: 'circle',
                symbolSize: 8,
                itemStyle: {
                    color: '#4facfe'
                },
                lineStyle: {
                    width: 3
                }
            },
            {
                name: '房源数量',
                type: 'bar',
                yAxisIndex: 1,
                data: sortedData.map(item => item.count || 0),
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        { offset: 0, color: '#00f2fe' },
                        { offset: 1, color: '#00c9ff' }
                    ])
                },
                barMaxWidth: 30
            }
        ]
    };

    charts.decadeChart.setOption(option);
    charts.decadeChart.hideLoading();
}

// 更新价格-面积相关性图表
function updateCorrelationChart(data) {
    if (!charts.correlationChart || !Array.isArray(data) || data.length === 0) {
        showChartError('correlationChart', '无有效数据');
        return;
    }

    // 提取相关性数据
    const districts = data.map(item => item.district || '未知区域');
    const priceAreaCorr = data.map(item => item.price_area_corr || 0);
    const unitPriceAreaCorr = data.map(item => item.unit_price_area_corr || 0);

    const option = {
        title: {
            text: '价格-面积相关性',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'axis',
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        legend: {
            data: ['总价面积相关性', '单价面积相关性'],
            textStyle: { color: '#a4b0be' },
            bottom: 0
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '12%',
            top: '15%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: districts,
            axisLine: {
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: {
                rotate: 30,
                color: '#a4b0be'
            }
        },
        yAxis: {
            type: 'value',
            name: '相关系数',
            nameTextStyle: { color: '#a4b0be' },
            axisLine: {
                show: true,
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: { color: '#a4b0be' },
            splitLine: {
                lineStyle: { color: 'rgba(100, 180, 255, 0.1)' }
            }
        },
        series: [
            {
                name: '总价面积相关性',
                type: 'line',
                data: priceAreaCorr,
                symbol: 'circle',
                symbolSize: 8,
                itemStyle: {
                    color: '#4facfe'
                },
                lineStyle: {
                    width: 3
                }
            },
            {
                name: '单价面积相关性',
                type: 'line',
                data: unitPriceAreaCorr,
                symbol: 'circle',
                symbolSize: 8,
                itemStyle: {
                    color: '#00f2fe'
                },
                lineStyle: {
                    width: 3
                }
            }
        ]
    };

    charts.correlationChart.setOption(option);
    charts.correlationChart.hideLoading();
}

// 更新区域面积分布图表
function updateDistrictAreaChart(data) {
    if (!charts.districtAreaChart || !Array.isArray(data) || data.length === 0) {
        showChartError('districtAreaChart', '无有效数据');
        return;
    }

    const districts = [...new Set(data.map(item => item.district || '未知区域'))];
    const areaSegments = [...new Set(data.map(item => item.area_segment || '未知面积'))].sort();

    // 创建系列数据
    const seriesData = [];
    districts.forEach(district => {
        const districtData = areaSegments.map(segment => {
            const item = data.find(d =>
                (d.district || '未知区域') === district &&
                (d.area_segment || '未知面积') === segment
            );
            return item ? (item.count || 0) : 0;
        });

        seriesData.push({
            name: district,
            type: 'bar',
            stack: 'total',
            emphasis: { focus: 'series' },
            data: districtData
        });
    });

    const option = {
        title: {
            text: '区域面积分布',
            left: 'center',
            textStyle: { color: '#e0e7ff' }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'shadow' },
            backgroundColor: 'rgba(20, 30, 65, 0.9)',
            borderColor: '#4facfe',
            textStyle: { color: '#e0e7ff' }
        },
        legend: {
            data: districts,
            textStyle: { color: '#a4b0be' },
            bottom: 0
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '15%',
            top: '15%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: areaSegments.map(seg => `${seg}㎡`),
            axisLine: {
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: {
                color: '#a4b0be',
                rotate: 30
            }
        },
        yAxis: {
            type: 'value',
            name: '房源数量',
            nameTextStyle: { color: '#a4b0be' },
            axisLine: {
                show: true,
                lineStyle: { color: '#4facfe' }
            },
            axisLabel: { color: '#a4b0be' },
            splitLine: {
                lineStyle: { color: 'rgba(100, 180, 255, 0.1)' }
            }
        },
        series: seriesData
    };

    charts.districtAreaChart.setOption(option);
    charts.districtAreaChart.hideLoading();
}

// ======================= 其他功能函数 =======================

// 窗口调整大小时重绘图表
function resizeCharts() {
    Object.values(charts).forEach(chart => {
        if (chart) chart.resize();
    });
}

// 初始化事件监听器
function initEventListeners() {
    // 添加过滤器按钮事件
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            document.querySelectorAll('.filter-btn').forEach(b =>
                b.classList.remove('active'));
            this.classList.add('active');

            // 重新加载数据
            fetchDataAndUpdateCharts();
        });
    });

    // 添加刷新按钮事件
    const refreshBtn = document.getElementById('refresh-btn');
    if (refreshBtn) {
        refreshBtn.addEventListener('click', function() {
            fetchDataAndUpdateCharts();
            showLoadingState();
        });
    }

    // 添加图表操作按钮事件
    document.querySelectorAll('.chart-action-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const chartId = this.getAttribute('data-chart');
            const chart = charts[chartId];

            if (!chart) return;

            if (this.textContent.includes('📥')) {
                // 下载图表为图片
                const url = chart.getDataURL({
                    type: 'png',
                    pixelRatio: 2,
                    backgroundColor: '#0f1b3a'
                });
                const link = document.createElement('a');
                link.href = url;
                link.download = `${chartId}_${new Date().toISOString().slice(0, 10)}.png`;
                link.click();
            } else if (this.textContent.includes('🔍')) {
                // 全屏查看
                chart.dispatchAction({
                    type: 'takeGlobalCursor',
                    key: 'dataZoomSelect',
                    dataZoomSelectActive: true
                });
            }
        });
    });

    // 窗口大小调整事件
    window.addEventListener('resize', resizeCharts);
}

// 确保 ECharts 已加载
function ensureEChartsLoaded() {
    return new Promise((resolve, reject) => {
        if (typeof echarts !== 'undefined') {
            resolve();
            return;
        }

        // 尝试从本地加载
        const localScript = document.createElement('script');
        localScript.src = 'js/echarts.min.js';

        localScript.onload = resolve;
        localScript.onerror = function() {
            // 本地加载失败，尝试从 CDN 加载
            const cdnScript = document.createElement('script');
            cdnScript.src = 'https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js';
            cdnScript.onload = resolve;
            cdnScript.onerror = reject;
            document.head.appendChild(cdnScript);
        };

        document.head.appendChild(localScript);
    });
}

// 主初始化函数
async function initializeApp() {
    // 初始化日期时间
    updateDateTime();
    setInterval(updateDateTime, 1000);

    try {
        // 确保 ECharts 已加载
        await ensureEChartsLoaded();

        // 初始化图表
        initCharts();

        // 初始化事件监听器
        initEventListeners();

        // 获取数据并更新图表
        await fetchDataAndUpdateCharts();

    } catch (error) {
        console.error('初始化失败:', error);
        showErrorState(`初始化失败: ${error.message}`);
    }
}

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', initializeApp);