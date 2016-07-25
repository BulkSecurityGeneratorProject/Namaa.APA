'use strict';

angular.module('namaaApaApp').controller('GrainType3DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GrainType3', 'User',
        function($scope, $stateParams, $modalInstance, entity, GrainType3, User) {

        $scope.grainType3 = entity;
            $scope.grainType3.watered=0.0;
            $scope.grainType3.nonWatered=0.0;
            $scope.grainType3.beans=0.0;
            $scope.grainType3.chickpeas=0.0;
            $scope.grainType3.cowpea=0.0;

            $scope.users = User.query();
        $scope.load = function(id) {
            GrainType3.get({id : id}, function(result) {
                $scope.grainType3 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('namaaApaApp:grainType3Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if($scope.grainType3.beans+$scope.grainType3.chickpeas+$scope.grainType3.cowpea+$scope.grainType3.lathyrus == $scope.grainType3.watered+$scope.grainType3.nonWatered) {
                if ($scope.grainType3.id != null) {
                    GrainType3.update($scope.grainType3, onSaveFinished);
                } else {
                    GrainType3.save($scope.grainType3, onSaveFinished);
                }
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
