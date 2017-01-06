var gulp = require('gulp');
var ts = require("gulp-typescript");
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');
var gutil = require('gulp-util');
const babel = require('gulp-babel');
var glob = require('glob');

var tsProject = ts.createProject("tsconfig.json");


gulp.task('default', ['compile-typescript'], function() {
    if(process.env.NODE_ENV === 'production') {
        console.log('gulp runs as expected on production');
    } else {
        console.log('gulp runs as expected');
    }
});

gulp.task('compile-typescript', function() {
    var tsFiles = glob.sync('./src/main/webapp/js/**/*.ts');
    var tsResult = gulp.src(tsFiles) // or tsProject.src()
        .pipe(sourcemaps.init())
        .pipe(tsProject());


    return tsResult.js
        .pipe(babel({
            presets: ['es2015']
        }))
        .pipe(gutil.env.type === 'production' ? uglify() : gutil.noop())
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(function(file) {
            return file.base;
        }));
});

gulp.task('watch', function() {
    gulp.watch("src/main/webapp/js/**/*.ts", ['compile-typescript']);
});