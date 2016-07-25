'use strict';

angular.module('namaaApaApp').controller('OilDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Oil', 'User',
        function($scope, $stateParams, $modalInstance, entity, Oil, User) {

        $scope.oil = entity;
            $scope.oil.watered=0.0;
            $scope.oil.nonWatered=0.0;
            $scope.oil.safflower=0.0;
            $scope.oil.sesame=0.0;
            $scope.oil.radish=0.0;

            $scope.users = User.query();
        $scope.load = function(id) {
            Oil.get({id : id}, function(result) {
                $scope.oil = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('namaaApaApp:oilUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
if($scope.oil.safflower+$scope.oil.sesame+$scope.oil.radish == $scope.oil.watered+$scope.oil.nonWatered) {
                if ($scope.oil.id != null) {
                    Oil.update($scope.oil, onSaveFinished);
                } else {
                    Oil.save($scope.oil, onSaveFinished);
                }
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
