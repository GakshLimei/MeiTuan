$(function(){
	echarts_1();
	map();
	wuran2()
    wuran();
    huaxing();
	zhexian();
	
/**
 * 右边下角图表：订单品类占比（备选）
 */
 function echarts_1() {

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echarts_1'));

    var colorList = [{
            type: 'linear',
            x: 0,
            y: 0,
            x2: 1,
            y2: 1,
            colorStops: [{
                    offset: 0,
                    color: 'rgba(51,192,205,0.01)' // 0% 处的颜色
                },
                {
                    offset: 1,
                    color: 'rgba(51,192,205,0.57)' // 100% 处的颜色
                }
            ],
            globalCoord: false // 缺省为 false
        },
        {
            type: 'linear',
            x: 1,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [{
                    offset: 0,
                    color: 'rgba(115,172,255,0.02)' // 0% 处的颜色
                },
                {
                    offset: 1,
                    color: 'rgba(115,172,255,0.67)' // 100% 处的颜色
                }
            ],
            globalCoord: false // 缺省为 false
        },
        {
            type: 'linear',
            x: 1,
            y: 0,
            x2: 0,
            y2: 0,
            colorStops: [{
                    offset: 0,
                    color: 'rgba(158,135,255,0.02)' // 0% 处的颜色
                },
                {
                    offset: 1,
                    color: 'rgba(158,135,255,0.57)' // 100% 处的颜色
                }
            ],
            globalCoord: false // 缺省为 false
        },
        {
            type: 'linear',
            x: 0,
            y: 1,
            x2: 0,
            y2: 0,
            colorStops: [{
                    offset: 0,
                    color: 'rgba(252,75,75,0.01)' // 0% 处的颜色
                },
                {
                    offset: 1,
                    color: 'rgba(252,75,75,0.57)' // 100% 处的颜色
                }
            ],
            globalCoord: false // 缺省为 false
        },
        {
            type: 'linear',
            x: 1,
            y: 1,
            x2: 1,
            y2: 0,
            colorStops: [{
                    offset: 0,
                    color: 'rgba(253,138,106,0.01)' // 0% 处的颜色
                },
                {
                    offset: 1,
                    color: '#FDB36ac2' // 100% 处的颜色
                }
            ],
            globalCoord: false // 缺省为 false
        },
        {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 1,
            y2: 0,
            colorStops: [{
                    offset: 0,
                    color: 'rgba(254,206,67,0.12)' // 0% 处的颜色
                },
                {
                    offset: 1,
                    color: '#FECE4391' // 100% 处的颜色
                }
            ],
            globalCoord: false // 缺省为 false
        }
    ]
    var colorLine = ['#33C0CD', '#73ACFF', '#9E87FF', '#FE6969', '#FDB36A', '#FECE43']

    function getRich() {
        let result = {}
        colorLine.forEach((v, i) => {
            result[`hr${i}`] = {
                backgroundColor: colorLine[i],
                borderRadius: 3,
                width: 3,
                height: 3,
                padding: [3, 3, 0, -12]
            }
            result[`a${i}`] = {
                padding: [8, -60, -20, -20],
                color: colorLine[i],
                fontSize: 12
            }
        })
        return result
    }
    let data = [{
        'name': '南方',
        'value': 35
    }, {
        'name': '江浙沪',
        'value': 33
    }, {
        'name': '北方',
        'value': 22
    }, {
        'name': '西南',
        'value': 10
    }].sort((a, b) => {
        return b.value - a.value
    })
    data.forEach((v, i) => {
        v.labelLine = {
            lineStyle: {
                width: 1,
                color: colorLine[i]
            }
        }
    })
    option = {
        // 图例
        legend: {
            orient: 'horizontal', // 布局方式，默认为水平布局，可选为：
            // 'horizontal' ¦ 'vertical'
            x: 'center', // 水平安放位置，默认为全图居中，可选为：
            // 'center' ¦ 'left' ¦ 'right'
            // ¦ {number}（x坐标，单位px）
            y: 'bottom', // 垂直安放位置，默认为全图顶端，可选为：
            // 'top' ¦ 'bottom' ¦ 'center'
            // ¦ {number}（y坐标，单位px）
            backgroundColor: 'rgba(0,0,0,0)',
            borderColor: '#ccc', // 图例边框颜色
            borderWidth: 0, // 图例边框线宽，单位px，默认为0（无边框）
            padding: 5, // 图例内边距，单位px，默认各方向内边距为5，
            // 接受数组分别设定上右下左边距，同css
            itemGap: 10, // 各个item之间的间隔，单位px，默认为10，
            // 横向布局时为水平间隔，纵向布局时为纵向间隔
            itemWidth: 20, // 图例图形宽度
            itemHeight: 14, // 图例图形高度
            textStyle: {
                color: '#fff', // 图例文字颜色
                fontSize: 18, // 图例文字大小
            }
        },
        series: [{
            type: 'pie',
            radius: '60%',
            center: ['50%', '50%'],
            clockwise: true,
            avoidLabelOverlap: true,
            label: {
                show: true,
                position: 'outside',
                formatter: function(params) {
                    const name = params.name
                    const percent = params.percent + '%'
                    const index = params.dataIndex
                    return [`{a${index}|${name}：${percent}}`, `{hr${index}|}`].join('\n')
                },
                rich: getRich()
            },
            itemStyle: {
                normal: {
                    color: function(params) {
                        return colorList[params.dataIndex]
                    }
                }
            },
            data,
            roseType: 'radius'
        }]
    }

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

}

	
	
})


function map(){
		var myChart = echarts.init(document.getElementById('map'));
		let iconRQ = "image://data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABUAAAAUCAYAAABiS3YzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjNCRkRBMEJBQzgwRjExRUFBNUI0RTMyMThEN0UxMzFEIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjNCRkRBMEJCQzgwRjExRUFBNUI0RTMyMThEN0UxMzFEIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6M0JGREEwQjhDODBGMTFFQUE1QjRFMzIxOEQ3RTEzMUQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6M0JGREEwQjlDODBGMTFFQUE1QjRFMzIxOEQ3RTEzMUQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz5mT42iAAABQ0lEQVR42mL8LabOQCQIBOL1xChkItJAkLp+IBajpqFWQCwPxJ7UNDQCSgdQy1BmIA6Bsl2AmJMahjoAsTiUzQPETtQwNAKN709IAwvUayZQ/hcg/o0k/x6Ig9D0+ABxKJT9HYh/oMm/BBm6GYitgTgfiBmJcLkkEK/CIXcGiGNB3v8JxIVQF31gIA/8AeIWaNK7gRymG4BYH4hPkGjgXSC2A+JaWNChR9QjqIJeIP5PhIGzgdgAiI8Tin2QbSVAvIOAgROBOA0auUQlKV4gtidgqBGp6RSUFrmIKA/ESDEUPcGfBOIUIH6Lln29iTVUCIjdkJJKExDbAPFcqJdPEMpd2AwF5TBWaFKxBeJ6qOHIqaMbmjrcsBUw2AwNh7rKAEeaBaWOMiD2BeJvQOxOyFBuaFJJwZZU0MBWaHCIo0sABBgAetA4Jx5t/ToAAAAASUVORK5CYII="

		let data = [{
		        name: "北京",
		        value: [116.24, 39.55, 100]
		    },
		    {
		        name: "深圳",
		        value: [114.271522, 22.753644]
		    },
		   
		    {
		        name: "重庆",
		        value: [106.54, 29.59]
		    },
		    // {
		    //     name: "浙江",
		    //     value: [120.19, 30.26]
		    // },
			{
				name: "上海",
				value: [121.4648, 31.2891]
			},

		
		]
		let LableData = [
		    {
		    name: "北京TopFive",
		    coords: [
		        [116.24, 39.55, 100],
		        [125.24, 40.55, 100]
		    ], // 线条位置[开始位置，结束位置]
		    value: ['汉堡王:700099', '南香城（和平里店）：500099','西贝莜面村(北京大悦城店)：500099',"三泉冷面(朝外店): 400099","付小姐在成都：400099"]
		}, 
		{
		    name: "深圳TopFive",
		    coords: [
		        [114.271522, 22.753644],
		        [114.24, 17.55, 100]
		    ], // 线条位置[开始位置，结束位置]
		    value: ["米已成粥·一碗神仙粥(南油店): 400099", "塔斯汀·中国汉堡(南山南油店): 400099","韦小堡•炸鸡汉堡(蛇口店): 400099","正宗老广式茶点粥云吞面: 400099","霸王别饥嫩牛五方·汉堡: 300099"]
		}, 
		{
		    name: "重庆TopFive",
		    coords: [
		        [106.54, 29.59],
		        [90.24, 51.55]
		    ], // 线条位置[开始位置，结束位置]
		    value: ["凉皮先生·肉夹馍·酸辣粉·米线: 200099", "茶百道(协信铜锣湾店): 200099","李氏串烧·烧烤·小龙虾·江湖菜(巴南店): 200099","茶百道(实验中学店): 200099","私町Osaka食堂(重庆南坪店): 200099"]
		}, 
		// {
		//     name: "浙江",
		//     coords: [
		//         [120.19, 30.26],
		//         [125.24, 27.55, 100]
		//     ], // 线条位置[开始位置，结束位置]
		//     value: [1021, 120]
		// },
			{
				name: "上海TopFive",
				coords: [
					[121.4648, 31.2891],
					[125.4648, 30.2891]
				], // 线条位置[开始位置，结束位置]
				value: ["小桃园（复兴中路店）：999999","零度低卡轻食沙拉：900099","懒猴子泡馍：800099","南星汇海南鸡饭：800099","博多新记粤菜馆：800099"]
			},
		];
		
		option = {
		    /*backgroundColor: '#000f1e',*/
		    geo: {
		        map: 'china',
		        aspectScale: 0.85,
		        layoutCenter: ["50%", "50%"], //地图位置
		        layoutSize: '100%',
		        itemStyle: {
		            normal: {
		                shadowColor: '#276fce',
		                shadowOffsetX: 0,
		                shadowOffsetY: 15,
		                opacity: 0.5,
		            },
		            emphasis: {
		                areaColor: '#276fce',
		
		            }
		        },
		        regions: [{
		            name: '南海诸岛',
		            itemStyle: {
		                areaColor: 'rgba(0, 10, 52, 1)',
		
		                borderColor: 'rgba(0, 10, 52, 1)',
		                normal: {
		                    opacity: 0,
		                    label: {
		                        show: false,
		                        color: "#009cc9",
		                    }
		                },
		
		
		            },
		            label: {
		                show: false,
		                color: '#FFFFFF',
		                fontSize: 12,
		            },
		
		
		        }],
		
		    },
		    series: [
		        // 常规地图
		        {
		            type: 'map',
		            mapType: 'china',
		            aspectScale: 0.85,
		            layoutCenter: ["50%", "50%"], //地图位置
		            layoutSize: '100%',
		            zoom: 1, //当前视角的缩放比例
		            // roam: true, //是否开启平游或缩放
		            scaleLimit: { //滚轮缩放的极限控制
		                min: 1,
		                max: 2
		            },
		            itemStyle: {
		                normal: {
		                    areaColor: '#0c274b',
		                    borderColor: '#1cccff',
		                    borderWidth: 1.5
		
		
		                },
		                emphasis: {
		                    areaColor: '#02102b',
		                    label: {
		                        color: "#fff"
		                    }
		
		                }
		            },
		
		
		        },
		        { //首都星图标
		            name: '首都',
		            type: 'scatter',
		            coordinateSystem: 'geo',
		            data: [{
		                name: '首都',
		                value: [116.24, 41.55, 100],
		
		            }, ],
		            symbol: iconRQ,
		            symbolSize: 20,
		            label: {
		                normal: {
		                    show: false,
		
		                },
		                emphasis: {
		                    show: false
		                }
		            },
		
		        },
		        // 区域散点图
		        {
		            type: 'effectScatter',
		            coordinateSystem: 'geo',
		            zlevel: 2,
		            symbolSize: 10,
		            rippleEffect: { //坐标点动画
		                period: 3,
		                scale: 5,
		                brushType: 'fill'
		            },
		            label: {
		                normal: {
		                    show: true,
		                    position: 'right',
		                    formatter: '{b}',
		                    color: '#b3e2f2',
		                    fontWeight: "bold",
		                    fontSize: 16
		                }
		            },
		
		            data: data,
		            itemStyle: { //坐标点颜色
		                normal: {
		                    show: true,
		                    color: '#ff8003',
		                    shadowBlur: 20,
		                    shadowColor: '#fff'
		                },
		                emphasis: {
		                    areaColor: '#f00'
		                }
		            },
		
		        },

		       
		         /*{
		            name: 'lable',
		            type: 'scatter',
		            coordinateSystem: 'geo',
		            symbol: 'pin',
		            symbolSize: [50,50],
		            label: {
		                normal: {
		                    show: true,
		                    textStyle: {
		                        color: '#fff',
		                        fontSize: 9,
		                    },
		                    formatter (value){
		                        return value.data.value[1]
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: '#D8BC37', //标志颜色
		                }
		            },
		            data: data,
		            showEffectOn: 'render',
		            rippleEffect: {
		                brushType: 'stroke'
		            },
		            hoverAnimation: true,
		            zlevel: 1
		        },*/
		        {
		
		            type: 'lines',
		            zlevel: 3,
		            symbol: 'circle',
		            symbolSize: [5, 5],
		            color: '#ff8003',
		            opacity: 1,
		            label: {
		                show: true,
		                padding: [10, 20],
		                color: '#fff',
		                backgroundColor: "#1a3961",
		                borderColor: '#aee9fb',
		                borderWidth: 1,
		                borderRadius: 6,
		                formatter(params) {

		                    let arr = [params.name, "1." + params.value[0] + "单", "2" + params.value[1] + "单","3."+params.value[2]+ "单","4."+params.value[3]+ "单","5."+params.value[4]+"单"];
		                    return arr.join("\n")
		                },
		                textStyle: {
		                    align: 'left',
		                    lineHeight: 20,
		                }
		                /* normal: {
		
		                     textStyle: {
		                         color: '#fff',
		                         fontSize: 9,
		                     },
		                     formatter (value){
		                         return value.data.value[2]
		                     },
		
		                 }*/
		            },
		            lineStyle: {
		                type: 'solid',
		                color: '#fff',
		                width: 0.5,
		                opacity: 1,
		
		            },
		            data: LableData,
		
		
		        },
		    ]
		};
		
		myChart.on('click', function(params) {
		    console.log(params);
		});
		myChart.setOption(option);
		window.addEventListener("resize",function(){
	        myChart.resize();
	    });
		
	}


function wuran2(){

	var myChart = echarts.init(document.getElementById('wuran2'));
	var salvProName =["上海市","武汉市","南京市","长沙市","杭州市","成都市"];
	var salvProValue =[76445046,62973042,48299174,48018863,44250063,43802113];
	var salvProMax =[];//背景按最大值
	for (let i = 0; i < salvProValue.length; i++) {
		salvProMax.push(salvProValue[0])
	}
	option = {

		grid: {
			left: '2%',
			right: '2%',
			bottom: '-6%',
			top: '8%',
			containLabel: true
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'none'
			},
			formatter: function(params) {
				return params[0].name  + ' : ' + params[0].value
			}
		},
		xAxis: {
			show: false,
			type: 'value'
		},
		yAxis: [{
			type: 'category',
			inverse: true,
			axisLabel: {
				show: true,
				textStyle: {
					color: '#fff'
				},
			},
			splitLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLine: {
				show: false
			},
			data: salvProName
		}, {
			type: 'category',
			inverse: true,
			axisTick: 'none',
			axisLine: 'none',
			show: true,
			axisLabel: {
				textStyle: {
					color: '#ffffff',
					fontSize: '12'
				},
			},
			data:salvProValue
		}],
		series: [{
			name: '值',
			type: 'bar',
			zlevel: 1,
			itemStyle: {
				normal: {
					barBorderRadius: 30,
					color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
						offset: 0,
						color: 'rgb(147,112,219,1)'
					}, {
						offset: 1,
						color: 'rgb(153,50,204,1)'
					}]),
				},
			},
			barWidth: 10,
			data: salvProValue
		},
			{
				name: '背景',
				type: 'bar',
				barWidth: 10,
				barGap: '-100%',
				data: salvProMax,
				itemStyle: {
					normal: {
						color: 'rgba(24,31,68,1)',
						barBorderRadius: 30,
					}
				},
			},
		]
	};

	myChart.setOption(option);


}


function wuran(){

	var myChart = echarts.init(document.getElementById('wuran'));
	var salvProName =["上海市","广东省","湖北省","江苏省","湖南省","浙江省"];
	var salvProValue =[76445046,65728939,62973042,48299174,48018863,44260567];
	var salvProMax =[];//背景按最大值
	for (let i = 0; i < salvProValue.length; i++) {
	    salvProMax.push(salvProValue[0])
	}
	option = {

	    grid: {
	        left: '2%',
	        right: '2%',
	        bottom: '-6%',
	        top: '8%',
	        containLabel: true
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'none'
	        },
	        formatter: function(params) {
	            return params[0].name  + ' : ' + params[0].value
	        }
	    },
	    xAxis: {
	        show: false,
	        type: 'value'
	    },
	    yAxis: [{
	        type: 'category',
	        inverse: true,
	        axisLabel: {
	            show: true,
	            textStyle: {
	                color: '#fff'
	            },
	        },
	        splitLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        data: salvProName
	    }, {
	        type: 'category',
	        inverse: true,
	        axisTick: 'none',
	        axisLine: 'none',
	        show: true,
	        axisLabel: {
	            textStyle: {
	                color: '#ffffff',
	                fontSize: '12'
	            },
	        },
	        data:salvProValue
	    }],
	    series: [{
	            name: '值',
	            type: 'bar',
	            zlevel: 1,
	            itemStyle: {
	                normal: {
	                    barBorderRadius: 30,
	                    color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
	                        offset: 0,
	                        color: 'rgb(57,89,255,1)'
	                    }, {
	                        offset: 1,
	                        color: 'rgb(46,200,207,1)'
	                    }]),
	                },
	            },
	            barWidth: 10,
	            data: salvProValue
	        },
	        {
	            name: '背景',
	            type: 'bar',
	            barWidth: 10,
	            barGap: '-100%',
	            data: salvProMax,
	            itemStyle: {
	                normal: {
	                    color: 'rgba(24,31,68,1)',
	                    barBorderRadius: 30,
	                }
	            },
	        },
	    ]
	};
	
	myChart.setOption(option);

	 
}

function huaxing(){
	var myChart = echarts.init(document.getElementById('huaxing'));
	
	var dataStyle = {
	    normal: {
	        label: {
	            show: false
	        },
	        labelLine: {
	            show: false
	        },
	        shadowBlur: 0,
	        shadowColor: '#203665'
	    }
	};
	option = {

	    series: [{
	        name: '第一个圆环',
	        type: 'pie',
	        clockWise: false,
	        radius: [45, 55],
	        itemStyle: dataStyle,
	        hoverAnimation: false,
	        center: ['15%', '50%'],
	        data: [{
	            value: 999999,
	            label: {
	                normal: {
	                    rich: {
	                        a: {
	                            color: '#3a7ad5',
	                            align: 'center',
	                            fontSize: 14,
	                            fontWeight: "bold"
	                        },
	                        b: {
	                            color: '#fff',
	                            align: 'center',
	                            fontSize: 12
	                        }
	                    },
	                    formatter: function(params){
	                        return "{b|最高月销量}\n\n"+"{a|"+params.value+"单}"+"\n\n{b|上海市}";
	                    },
	                    position: 'center',
	                    show: true,
	                    textStyle: {
	                        fontSize: '12',
	                        fontWeight: 'normal',
	                        color: '#fff'
	                    }
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#2c6cc4',
	                    shadowColor: '#2c6cc4',
	                    shadowBlur: 0
	                }
	            }
	        }, {
	            value: 99995,
	            name: 'invisible',
	            itemStyle: {
	                normal: {
	                    color: '#24375c'
	                },
	                emphasis: {
	                    color: '#24375c'
	                }
	            }
	        }]
	    }, {
	        name: '第二个圆环',
	        type: 'pie',
	        clockWise: false,
	        radius: [45, 55],
	        itemStyle: dataStyle,
	        hoverAnimation: false,
	        center: ['50%', '50%'],
	        data: [{
	            value: 45761,
	            label: {
	                normal: {
	                    rich: {
	                        a: {
	                            color: '#d03e93',
	                            align: 'center',
	                            fontSize: 14,
	                            fontWeight: "bold"
	                        },
	                        b: {
	                            color: '#fff',
	                            align: 'center',
	                            fontSize: 12
	                        }
	                    },
	                    formatter: function(params){
	                        return "{b|最小售价总和}\n\n"+"{a|"+params.value+"元}"+"\n\n{b|广东省}";
	                    },
	                    position: 'center',
	                    show: true,
	                    textStyle: {
	                        fontSize: '12',
	                        fontWeight: 'normal',
	                        color: '#fff'
	                    }
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#ef45ac',
	                    shadowColor: '#ef45ac',
	                    shadowBlur: 0
	                }
	            }
	        }, {
	            value: 26666,
	            name: 'invisible',
	            itemStyle: {
	                normal: {
	                    color: '#412a4e'
	                },
	                emphasis: {
	                    color: '#412a4e'
	                }
	            }
	        }]
	    }, {
	        name: '第三个圆环',
	        type: 'pie',
	        clockWise: false,
	        radius: [45, 55],
	        itemStyle: dataStyle,
	        hoverAnimation: false,
	        center: ['85%', '50%'],
	        data: [{
	            value: 227214,
	            label: {
	                normal: {
	                    rich: {
	                        a: {
	                            color: '#603dd0',
	                            align: 'center',
	                            fontSize: 14,
	                            fontWeight: "bold"
	                        },
	                        b: {
	                            color: '#fff',
	                            align: 'center',
	                            fontSize: 12
	                        }
	                    },
	                    formatter: function(params){
	                        return "{b|配送费总和}\n\n"+"{a|"+params.value+"元}"+"\n\n{b|广东省}";
	                    },
	                    position: 'center',
	                    show: true,
	                    textStyle: {
	                        fontSize: '12',
	                        fontWeight: 'normal',
	                        color: '#fff'
	                    }
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#613fd1',
	                    shadowColor: '#613fd1',
	                    shadowBlur: 0
	                }
	            }
	        }, {
	            value: 266665,
	            name: 'invisible',
	            itemStyle: {
	                normal: {
	                    color: '#453284'
	                },
	                emphasis: {
	                    color: '#453284'
	                }
	            }
	        }]
	    }]
	}
	
	myChart.setOption(option);

	 
}


function zhexian() {
	var myChart = echarts.init(document.getElementById('zhexian'));

	dataText = [{
		name: '最小售价总和',
		type: 'line',
		smooth: true,
		symbolSize: 8,
		data: [31252, 40026, 30623, 39122, 45761, 32052],
		barWidth: '30%',
		itemStyle: {
			normal: {
				color: '#f0c725'
			}
		}
	}, {
		name: '配送费总和',
		type: 'line',
		smooth: true,
		symbolSize: 8,
		data: [121473, 116419,104052, 117206, 227214, 118780],
		barWidth: '30%',
		itemStyle: {
			normal: {
				color: '#16f892'
			}
		}
	},

	]
	dataObj = {
		year: ['上海', '北京', '四川', '天津', '广东', '江苏','河北','浙江','湖北','湖南','辽宁','重庆','陕西'],
		data: {
			value: [{
				name: '最小售价总和',
				value: [23763, 25572, 23553, 25202, 41388, 21170,20,31474,25501,23736,21680,11930,12090]
			}, {
				name: '配送费总和',
				value: [2116, 3910,3262, 1968, 3128, 847,0,6495,2855,2112,1631,3247,2624]
			}
				// , {	name: '平均流速',
				// value: [127, 74, 120, 99, 130, 50]}
			]
		}
	}
	let dataArr = []

	dataObj.data.value.map(item => {
		let datachild = []
		let newArr = {
			name: item.name,
			type: 'line',
			smooth: true,
			symbolSize: 8,
			data: item.value,
			barWidth: '30%',
			itemStyle: {
				normal: {
					color: item.name === '最小售价总和' ? '#f0c725' : item.name === '配送费总和' ? '#0BE3FF' : '#16f892'
				}
			}
		}

		dataArr.push(newArr)
	})
	option = {
		color: ['#f0c725', '#16f892'],
		title: {
			left: 'center',
			text: '',
			textStyle: {
				color: '#FFFFFF',
				fontSize: '14',
			}
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		legend: {
			x: 'center',
			top: '25',
			textStyle: {
				color: '#c1cadf',
				"fontSize": 14
			}
		},
		grid: {
			left: '6%',
			right: '4%',
			top: '25%',
			bottom: '3%',
			containLabel: true
		},
		toolbox: {
			show: true,
			orient: 'vertical',
			x: 'right',
			y: 'center'
		},
		xAxis: [{
			type: 'category',
			boundaryGap: false,
			data: dataObj.year,
			axisLine: {
				lineStyle: {
					color: 'rgba(240,199,37,0.5)'
				}
			},
			axisLabel: {
				interval: 0,
				color: '#c1cadf',
				fontSize: '15'
			}
		}],
		yAxis: [{
			type: 'value',
			name: '(元)',
			nameTextStyle: {
				color: '#c1cadf',
				align: 'right',
				lineHeight: 10
			},
			axisLine: {
				lineStyle: {
					color: 'rgba(240,199,37,0.5)'
				}
			},
			axisLabel: {
				interval: 0,
				color: '#c1cadf',
				fontSize: '15'
			},
			splitLine: {
				show: false
			}
		}],
		series: dataArr
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}