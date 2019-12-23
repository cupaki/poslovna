var gulp = require('gulp');
var jshint = require('gulp-jshint');
var jscs = require('gulp-jscs');
var nodemon = require('gulp-nodemon');

var jsFiles = ['*.js', 'src/**/*.js', 'public/asset/**/.*js'];



gulp.task('inject', function() {
    var wiredep = require('wiredep').stream;
    var inject = require('gulp-inject');
    var options = {
        bowerJson: require('./bower.json'),
        directory: './public/lib',
        ignorePath: '../../public'
    };

    var injectSrc = gulp.src(['./public/Controllers/*.js', './public/Services/*.js'], {read: false});
    var injectOptions = {
        ignorePath: '/public'
    };

    return gulp.src('./public/*.html')
    .pipe(wiredep(options))
    .pipe(inject(injectSrc, injectOptions))
    .pipe(gulp.dest('./public'));
});

gulp.task('serve', ['inject'], function() {
    var options = {
        delayTime: 1,
        env: {
            'PORT' : 5000
        },
        watch: jsFiles
    };

    return nodemon(options)
    .on('restart', function(ev) {
        console.log('Restarting...');
    });
});
