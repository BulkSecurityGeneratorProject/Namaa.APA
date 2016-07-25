'use strict';

angular.module('namaaApaApp')
    .controller('FruitDetailController', function ($scope, $rootScope, $stateParams, entity, Fruit, User) {
        $scope.fruit = entity;
        $scope.load = function (id) {
            Fruit.get({id: id}, function(result) {
                $scope.fruit = result;
            });
        };
        $rootScope.$on('namaaApaApp:fruitUpdate', function(event, result) {
            $scope.fruit = result;
        });
    });
