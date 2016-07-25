'use strict';

angular.module('namaaApaApp')
    .controller('GrainType3DetailController', function ($scope, $rootScope, $stateParams, entity, GrainType3, User) {
        $scope.grainType3 = entity;
        $scope.load = function (id) {
            GrainType3.get({id: id}, function(result) {
                $scope.grainType3 = result;
            });
        };
        $rootScope.$on('namaaApaApp:grainType3Update', function(event, result) {
            $scope.grainType3 = result;
        });
    });
