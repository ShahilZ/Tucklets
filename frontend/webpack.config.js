module.exports = {
    entry: [
        './jsx/Main.jsx'
    ],
    output: {
        filename: 'bundle.js'
    },
    mode: 'development',
    devtool: 'source-map',
    module: {
        rules: [
        {
            test: /\.(js|jsx)$/,
            exclude: /node_modules/,
            loader: "babel-loader",
            options: {
                presets: ['@babel/preset-env', '@babel/preset-react'],
            }
        },
        {   test: /\.scss$/,
            use: [
                // Creates `style` nodes from JS strings
                {loader: 'style-loader'},
                // Translates CSS into CommonJS
                {loader: 'css-loader'},
                // Compiles Sass to CSS
                {loader: 'sass-loader'},
            ]
        },
        ],
  },
    resolve: {
        extensions: ['.js', '.jsx', '.scss']
    }
};