'use strict';

angular.module('namaaApaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


