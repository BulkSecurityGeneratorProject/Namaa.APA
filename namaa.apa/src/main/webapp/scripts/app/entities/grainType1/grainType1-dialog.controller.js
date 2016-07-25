'use strict';

angular.module('namaaApaApp').controller('GrainType1DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GrainType1', 'User',
        function($scope, $stateParams, $modalInstance, entity, GrainType1, User) {

        $scope.grainType1 = entity;
            $scope.grainType1.watered=0.0;
            $scope.grainType1.nonWatered=0.0;
            $scope.grainType1.corn=0.0;
            $scope.grainType1.millet=0.0;
            $scope.grainType1.spelt=0.0;

            $scope.users = User.query();
        $scope.load = function(id) {
            GrainType1.get({id : id}, function(result) {
                $scope.grainType1 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('namaaApaApp:grainType1Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if($scope.grainType1.corn+$scope.grainType1.millet+$scope.grainType1.spelt == $scope.grainType1.watered+$scope.grainType1.nonWatered) {
                if ($scope.grainType1.id != null) {
                    GrainType1.update($scope.grainType1, onSaveFinished);
                } else {
                    GrainType1.save($scope.grainType1, onSaveFinished);
                }
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
