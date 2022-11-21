import React from 'react';
import { Link } from 'react-router-dom';
function NotFound(props) {
    return (
        <section className="tw-bg-white tw-dark:bg-gray-900 tw-h-screen">
            <div className="tw-py-8 tw-px-4 tw-mx-auto tw-max-w-screen-xl tw-lg:py-16 tw-lg:px-6">
                <div className="tw-mx-auto tw-max-w-screen-sm tw-text-center">
                    <h1 className="tw-mb-4 tw-text-7xl tw-tracking-tight tw-font-extrabold tw-lg:text-9xl tw-text-primary-600 tw-dark:text-primary-500">
                        404
                    </h1>
                    <p className="tw-mb-4 tw-text-3xl tw-tracking-tight tw-font-bold tw-text-gray-900 tw-md:text-4xl tw-dark:text-white">
                        Something's missing.
                    </p>
                    <p className="tw-mb-4 tw-text-xl tw-font-light tw-text-gray-500 tw-dark:text-gray-400">
                        Sorry, we can't find that page. You'll find lots to explore on the home page.
                    </p>
                    <Link
                        to="/"
                        className="tw-inline-flex tw-text-blue-700 tw-bg-primary-600 tw-hover:bg-primary-800 tw-focus:ring-4 tw-focus:outline-none tw-focus:ring-primary-300 tw-font-medium tw-rounded-lg tw-text-xl tw-px-5 tw-py-2.5 tw-text-center tw-dark:focus:ring-primary-900 tw-my-4"
                    >
                        Back to Homepage
                    </Link>
                </div>
            </div>
        </section>
    );
}

export default NotFound;
