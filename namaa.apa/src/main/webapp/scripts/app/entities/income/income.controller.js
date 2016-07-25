'use strict';

angular.module('namaaApaApp')
    .controller('IncomeController', function ($scope, Income) {
        $scope.fruits = [];
        $scope.grainType1 = [];
        $scope.grainType2s = [];
        $scope.grainType3s = [];
        $scope.oil = [];
        $scope.olives = [];
        $scope.total = 0.0;


        $scope.loadAll = function() {
            // Build the chart

            Income.get(function(result) {
                $scope.incomes = result;
                var crops = [];
                angular.forEach( $scope.incomes.fruit[0],
                function(value, key) {if(key === "grapes" || key === "dates")
                    this.push( {"name":key, "y": value});
                    }, crops);
                angular.forEach( $scope.incomes.grainType1[0],
                    function(value, key) {if(key === "spelt" || key === "corn"|| key === "millet")
                        this.push( {"name":key, "y": value});
                    }, crops);
                angular.forEach( $scope.incomes.grainType2[0],
                    function(value, key) {if(key === "wheat" || key === "barley")
                        this.push( {"name":key, "y": value});
                    }, crops);
                angular.forEach( $scope.incomes.grainType3[0],
                    function(value, key) {if(key === "chickpeas" || key === "beans" || key === "cowpea" || key === "lathyrus")
                        this.push( {"name":key, "y": value});
                    }, crops);
                angular.forEach( $scope.incomes.oil[0],
                    function(value, key) {if(key === "sesame" || key === "safflower" || key === "radish")
                        this.push( {"name":key, "y": value});
                    }, crops);
                angular.forEach( $scope.incomes.olive[0],
                    function(value, key) {if(key === "oliveOil")
                        this.push( {"name":key, "y": value});
                    }, crops);

                console.log(crops);

                angular.element('#charts').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false,
                        type: 'pie'
                    },
                    title: {
                        text: 'Your Crops Income'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    },
                    series: [{
                        name: 'Crops',
                        colorByPoint: true,
                        data: crops
                    }]
                });
            });

        };
        $scope.loadAll();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.incomes = {total : 0.0, fruits: null, grains1: null, grains2: null, grains3: null, oil: null, olive: null};

        };
    });
